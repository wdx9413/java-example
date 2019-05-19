package cn.dox.anno_obj;

import cn.dox.annotation.Autowired;
import cn.dox.annotation.Bean;

@Bean
public class Building {
    @Autowired
    private Room room;

    @Override
    public String toString() {
        return "Building{" +
                "room=" + room +
                '}';
    }
}
