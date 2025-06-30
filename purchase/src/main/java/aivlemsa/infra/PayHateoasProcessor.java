package aivlemsa.infra;

import aivlemsa.domain.GetPay;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class PayHateoasProcessor implements RepresentationModelProcessor<EntityModel<GetPay>> {

    @Override
    public EntityModel<GetPay> process(EntityModel<GetPay> model) {
        model.add(
            Link.of(model.getRequiredLink("self").getHref() + "/status")
                .withRel("purchaseStatus")
        );
        return model;
    }
}

