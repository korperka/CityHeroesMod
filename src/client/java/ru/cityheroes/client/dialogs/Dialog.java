package ru.cityheroes.client.dialogs;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class Dialog {
    private String id;
    private List<String> phrases;
}
