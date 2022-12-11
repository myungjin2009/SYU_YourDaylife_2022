package com.example.ui.Module;

//Fragment, Activity 사이에서의 각종 커스텀 이벤트 들을 받기 위한 객체
public class CustomEventBus {
    public static class CustomOnClickEvent {
        public final String currentEvent;

        public CustomOnClickEvent(String currentEvent) {
            this.currentEvent = currentEvent;
        }
    }
}
