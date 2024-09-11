package kz.mb.project.mb_project.service.helper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.mb.project.mb_project.entity.BusinessTypeSpr;
import kz.mb.project.mb_project.entity.warehouse.ProductCategorySpr;
import kz.mb.project.mb_project.repo.BusinessTypeRepository;
import kz.mb.project.mb_project.repo.ProductCategoryRepository;

@Service
public class ProductCategoryParserService {

    private final ProductCategoryRepository productCategoryRepository;

    private final BusinessTypeRepository businessTypeRepository;

    @Autowired
    public ProductCategoryParserService(ProductCategoryRepository productCategoryRepository,
        BusinessTypeRepository businessTypeRepository) {
        this.productCategoryRepository = productCategoryRepository;
        this.businessTypeRepository = businessTypeRepository;
    }

    public void parseProductCategory() throws IOException {
        File jsonFile = new File("/Users/aishaabylgazy/Documents/mbbusiness/mb_project/src/main/resources/productCat/food.json");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonFile);
        ProductCategorySpr head = new ProductCategorySpr();
        BusinessTypeSpr type = businessTypeRepository.findById(UUID.fromString("ca5a0e85-b114-4fb9-9a85-5ce97824c699")).get();
        List<BusinessTypeSpr> types = new ArrayList<>();
        types.add(type);

        for (Iterator<JsonNode> it = jsonNode.elements(); it.hasNext(); ) {
            JsonNode node = it.next();
             head = parseJson(node, head, types);
        }
        productCategoryRepository.save(head);

    }

    private static ProductCategorySpr parseJson(JsonNode jsonNode, ProductCategorySpr productCategory, List<BusinessTypeSpr> types) {

        productCategory.setValueRU(jsonNode.path("title").asText());
        productCategory.setValueKZ(jsonNode.path("title").asText());
        productCategory.setId(UUID.randomUUID());
        productCategory.setBusinessType(types);


        // Рекурсивно обрабатываем подкатегории (если они есть)
        JsonNode itemsNode = jsonNode.path("items");
        if (itemsNode.isArray()) {
            List<ProductCategorySpr> subCategories = new ArrayList<>();
            for (JsonNode subCategoryNode : itemsNode) {
                ProductCategorySpr subCategory = parseJson(subCategoryNode, new ProductCategorySpr(), types);
                subCategory.setParent(productCategory);
                subCategories.add(subCategory);
            }
            productCategory.setSubCategory(subCategories);
        }

        // Далее, вы можете продолжить с остальными полями вашего объекта ProductCategorySpr

        return productCategory;
    }
}
