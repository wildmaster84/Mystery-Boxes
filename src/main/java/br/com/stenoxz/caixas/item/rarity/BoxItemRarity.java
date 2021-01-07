package br.com.stenoxz.caixas.item.rarity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class BoxItemRarity {

    @Setter
    private String name, displayName;

    @Setter
    private int percentage;
}
