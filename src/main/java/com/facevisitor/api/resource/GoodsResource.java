package com.facevisitor.api.resource;

import com.facevisitor.api.domain.goods.Goods;
import com.facevisitor.api.owner.controller.GoodsController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class GoodsResource extends EntityModel<Goods> {
    public GoodsResource(Goods content, Link... links) {
        super(content, links);
        add(linkTo(GoodsController.class).slash(content.getId()).withSelfRel());
        add(linkTo(GoodsController.class).slash(content.getId()).withRel("update"));
        add(linkTo(GoodsController.class).slash(content.getId()).withRel("delete"));
    }
}
