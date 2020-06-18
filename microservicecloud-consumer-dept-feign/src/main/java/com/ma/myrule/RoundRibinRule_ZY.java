package com.ma.myrule;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.RoundRobinRule;
import com.netflix.loadbalancer.Server;
/**
 * 问题：依旧轮询策略，但是加上新需求，每个服务器要求被调用5次。
 * 也即以前是每台机器一次，现在是每台机器5次
 * @author mgh_2
 *
 */
public class RoundRibinRule_ZY extends AbstractLoadBalancerRule {
    private static Logger log = LoggerFactory.getLogger(RoundRobinRule.class);

    private AtomicInteger nextServerCyclicCounter;// 当前提供服务的机器号
    private AtomicInteger total;// 分析：我们5次，但是微服务只有8001，8002，8003三台，OK？
    
	public RoundRibinRule_ZY() {
		this.nextServerCyclicCounter = new AtomicInteger(0);
		this.total = new AtomicInteger(0);
	}

	@Override
	public Server choose(Object key) {
		return choose(getLoadBalancer(), key);
	}

	private Server choose(ILoadBalancer lb, Object key) {
		if (lb == null) {
            log.warn("no load balancer");
            return null;
        }

        Server server = null;
        int count = 0;
        while (server == null && count++ < 10) {//如果没有服务，则尝试10次
            List<Server> reachableServers = lb.getReachableServers();//获取状态为UP的服务列表
            List<Server> allServers = lb.getAllServers();//获取所有服务
            int upCount = reachableServers.size();
            int serverCount = allServers.size();

            if ((upCount == 0) || (serverCount == 0)) {
                log.warn("No up servers available from load balancer: " + lb);
                return null;
            }

            int nextServerIndex = 0;
            if (total.intValue() < 5) {
            	total.incrementAndGet();
            	nextServerIndex = nextServerCyclicCounter.get();
			}else {
				total.set(0);
				nextServerIndex = incrementAndGetModulo(serverCount);//获取当前服务index
			}
            server = allServers.get(nextServerIndex);

            if (server == null) {
                /* Transient. */
                Thread.yield();
                continue;
            }

            if (server.isAlive() && (server.isReadyToServe())) {
                return (server);
            }

            // Next.
            server = null;
        }

        if (count >= 10) {
            log.warn("No available alive servers after 10 tries from load balancer: "
                    + lb);
        }
        return server;
	}
	
	private int incrementAndGetModulo(int modulo) {
        for (;;) {
            int current = nextServerCyclicCounter.get();
            int next = (current + 1) % modulo;
            if (nextServerCyclicCounter.compareAndSet(current, next))
                return next;
        }
    }
	
	@Override
	public void initWithNiwsConfig(IClientConfig clientConfig) {
	}


}
