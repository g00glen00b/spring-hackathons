package codes.dimitri.apps.recipe.recipe;

import codes.dimitri.apps.recipe.chef.Chef;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Recipe {
    @Id
    private UUID id;
    private String title;
    private String description;
    @ManyToOne
    @JoinColumn(name = "chef_id")
    private Chef chef;
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeImage> images;

    public Recipe(Chef chef, String title, String description) {
        this(UUID.randomUUID(), title, description, chef, new ArrayList<>());
    }

    public List<RecipeImage> getImages() {
        return List.copyOf(this.images);
    }

    public RecipeImage addImage(RecipeImage image) {
        this.images.add(image);
        image.setRecipe(this);
        return image;
    }
}
