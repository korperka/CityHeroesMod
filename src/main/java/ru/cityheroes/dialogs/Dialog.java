package ru.cityheroes.dialogs;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class Dialog {
    private String id;
    private List<String> phrases;
}
