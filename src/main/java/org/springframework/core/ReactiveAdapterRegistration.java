package org.springframework.core;

import org.moodminds.emission.Emittable;
import org.moodminds.reactive.Publishable;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import static org.moodminds.emission.Emittable.emittable;
import static org.moodminds.reactive.FluxPublishable.flux;
import static org.moodminds.reactive.Publishable.publishable;
import static org.springframework.core.ReactiveAdapterRegistry.getSharedInstance;
import static org.springframework.core.ReactiveTypeDescriptor.multiValue;
import static org.springframework.util.ClassUtils.isPresent;
import static reactor.core.publisher.Flux.from;

/**
 * The {@link Emittable} and {@link Publishable} Reactive Adapter Registration configuration bean.
 */
@Configuration
public class ReactiveAdapterRegistration implements InitializingBean {

    private static final List<Consumer<ReactiveAdapterRegistry>> REGISTRARS = new LinkedList<>();

    static {

        ClassLoader classLoader = ReactiveAdapterRegistration.class.getClassLoader();

        if (isPresent("org.moodminds.emission.Emittable", classLoader))
            REGISTRARS.add(registry -> registry.registerReactiveType(
                    multiValue(Emittable.class, () -> emittable(publishable())),
                    emittable -> publishable((Emittable<?, ?>) emittable),
                    publisher -> emittable(flux(from(publisher)))
            ));

        if (isPresent("org.moodminds.reactive.Publishable", classLoader))
            REGISTRARS.add(registry -> registry.registerReactiveType(
                    multiValue(Publishable.class, Publishable::publishable),
                    publishable -> (Publishable<?, ?>) publishable,
                    publisher -> flux(from(publisher))
            ));
    }

    /**
     * The reactive adapter registry bean holder field.
     */
    private final ReactiveAdapterRegistry registry;

    /**
     * Construct the configuration bean object with the specified {@link ReactiveAdapterRegistry}.
     *
     * @param registry the specified {@link ReactiveAdapterRegistry}
     */
    public ReactiveAdapterRegistration(@Autowired(required = false) ReactiveAdapterRegistry registry) {
        this.registry = registry;
    }

    /**
     * Register the reactive types in the available {@link ReactiveAdapterRegistry}
     * instances at the end of the bean initialization.
     */
    @Override
    public void afterPropertiesSet() {

        ReactiveAdapterRegistry sharedRegistry = getSharedInstance();

        REGISTRARS.forEach(registrar -> {
            registrar.accept(sharedRegistry);
            if (registry != null && registry != sharedRegistry)
                registrar.accept(registry);
        });
    }
}
