package aivlemsa.infra;

import aivlemsa.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class GenerateHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Generate>> {

    @Override
    public EntityModel<Generate> process(EntityModel<Generate> model) {
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
