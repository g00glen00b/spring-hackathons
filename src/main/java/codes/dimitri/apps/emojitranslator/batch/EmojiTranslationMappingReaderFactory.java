package codes.dimitri.apps.emojitranslator.batch;

import codes.dimitri.apps.emojitranslator.EmojiTranslatorProperties;
import codes.dimitri.apps.emojitranslator.data.EmojiTranslationMapping;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.support.CompositeItemReader;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class EmojiTranslationMappingReaderFactory implements FactoryBean<ItemReader<Optional<EmojiTranslationMapping>>> {
    private final EmojiTranslatorProperties properties;

    public EmojiTranslationMappingReaderFactory(EmojiTranslatorProperties properties) {
        this.properties = properties;
    }

    private FlatFileItemReader<Optional<EmojiTranslationMapping>> mapResourceToReader(EmojiTranslatorProperties.MappingResource mappingResource) {
        FlatFileItemReader<Optional<EmojiTranslationMapping>> reader = new FlatFileItemReader<>();
        reader.setResource(mappingResource.resource());
        reader.setLineMapper(new PatternLineMapper(mappingResource.pattern()));
        return reader;
    }

    @Override
    public ItemReader<Optional<EmojiTranslationMapping>> getObject() {
        List<ItemStreamReader<? extends Optional<EmojiTranslationMapping>>> delegates = new ArrayList<>();
        properties.mappingResources().stream().map(this::mapResourceToReader).forEach(delegates::add);
        return new CompositeItemReader<>(delegates);
    }

    @Override
    public Class<?> getObjectType() {
        return CompositeItemReader.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
