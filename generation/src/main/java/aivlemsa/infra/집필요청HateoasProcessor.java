package aivlemsa.infra;

import aivlemsa.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class 집필요청HateoasProcessor
    implements RepresentationModelProcessor<EntityModel<집필요청>> {

    @Override
    public EntityModel<집필요청> process(EntityModel<집필요청> model) {
        model.add(
            Link
                .of(
                    model.getRequiredLink("self").getHref() +
                    "/requestbookpublication"
                )
                .withRel("requestbookpublication")
        );

        return model;
    }
}
