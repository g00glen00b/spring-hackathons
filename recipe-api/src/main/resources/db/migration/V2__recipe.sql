create table recipe (
    id uuid not null,
    title varchar(128) not null,
    description varchar(512) not null,
    chef_id uuid not null,
    constraint pk_recipe primary key (id),
    constraint fk_recipe_chef foreign key (chef_id) references chef(id)
);

create table recipe_image (
    id uuid not null,
    format varchar(16) not null,
    recipe_id uuid not null,
    constraint pk_recipe_image primary key (id),
    constraint fk_recipe_image_recipe foreign key (recipe_id) references recipe(id)
);