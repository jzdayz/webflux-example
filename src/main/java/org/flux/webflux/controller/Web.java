package org.flux.webflux.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * @author ☞ 🏀 huqingfeng
 * @date 2019-07-29
 */
@Controller
public class Web extends ApplicationObjectSupport {

//    @RequestMapping("/**")
//    public String getAll(Model model){
//        model.addAttribute("name","虎");
//        return "index";
//    }

//    @Bean
//    public RouterFunction<ServerResponse> routerFunction(){
//        return (ServerRequest request)->
//                Mono.just((HandlerFunction<ServerResponse>)(res)->ServerResponse.ok().syncBody("11"));
//    }

    private static final ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(
            1,1,0L, TimeUnit.DAYS, new LinkedBlockingQueue<>()
    );


    @GetMapping("/sse")
    public SseEmitter sse() {
        SseEmitter emitter = new SseEmitter();
        EXECUTOR_SERVICE.submit(() -> {
            try {
                for (int i = 0; i < 1000000; i++) {
                    Thread.sleep(1000);
                    emitter.send(String.format("%s 第%s个数据",Thread.currentThread().getName(),i));
                }
                emitter.complete();
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        });
        return emitter;
    }

}
