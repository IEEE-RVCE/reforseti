package org.ieeervce.api.siterearnouveau.dto.image;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ImageDescriptionDTO {
    private int imageId;
    private int eventCategory;
    private String altText;
}
