package cn.dox.jdbcdemo.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author: weidx
 * @date: 2019/7/28
 */

@Data
public class User {
    private Long id;
    private String username;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
}
