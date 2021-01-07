package br.com.stenoxz.caixas.factory;

import br.com.stenoxz.caixas.box.Box;
import br.com.stenoxz.caixas.type.BoxType;

public class BoxFactory {

    public Box newBox(BoxType type){
        return Box.builder()
                .type(type)
                .build();
    }
}
