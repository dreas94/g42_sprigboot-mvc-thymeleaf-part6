package se.lexicon.mvcthymeleaf.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Error
{

    private final LocalDateTime dateTime;
    private String code;
    private String message;

    public Error()
    {
        dateTime = LocalDateTime.now();
    }

    public Error(String code, String message)
    {
        this();
        this.code = code;
        this.message = message;
    }


}
