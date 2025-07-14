package codes.dimitri.apps.emojitranslator.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmojiTranslationMappingRepository extends JpaRepository<EmojiTranslationMapping, String> {
}
