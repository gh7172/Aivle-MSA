package aivlemsa.infra;

import aivlemsa.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class 유저HateoasProcessor
    implements RepresentationModelProcessor<EntityModel<유저>> {

    @Override
    public EntityModel<유저> process(EntityModel<유저> model) {
        return model;
    }
}
