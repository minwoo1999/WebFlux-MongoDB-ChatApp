package com.cos.chatapp;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.awt.*;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatRepository chatRepository;

    //귓속말 할때 사용하면 됨
    // TEXT_EVENT_STREAM_VALUE 응답을 계속 흘려줌
    @CrossOrigin
    @GetMapping(value="/sender/{sender}/receiver/{receiver}",produces= MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat> getMsg(@PathVariable String sender,@PathVariable String receiver){
        return chatRepository.mFindBySender(sender,receiver)
                .subscribeOn(Schedulers.boundedElastic());
    }

    //Mono는 데이터를 한번만 리턴시킨다.
    @CrossOrigin
    @PostMapping("/chat")
    public Mono<Chat> setMsg(@RequestBody Chat chat){
        chat.setCreatedAt(LocalDateTime.now());
        return chatRepository.save(chat);
    }

    //채팅 내역을 전부조회
    @CrossOrigin
    @GetMapping(value = "/chat/roomNum/{roomNum}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat> findByRoomNum(@PathVariable Integer roomNum) {
        return chatRepository.mFindByRoomNum(roomNum)
                .subscribeOn(Schedulers.boundedElastic());
    }
}
