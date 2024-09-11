package kz.mb.project.mb_project.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kz.mb.project.mb_project.service.helper.ProductCategoryParserService;


@RestController
@RequestMapping("/api/helper")
public class HelperController {
  private final ProductCategoryParserService productCategoryParser;

  @Autowired
  public HelperController(ProductCategoryParserService productCategoryParser) {
    this.productCategoryParser = productCategoryParser;
  }

  @RequestMapping(
      value = "/public/parseProdCat",
      method = RequestMethod.GET
  )
  @ResponseStatus(HttpStatus.OK)
  public void parseProdCat() throws IOException {
    productCategoryParser.parseProductCategory();
  }
}
