package org.jnyou.gmall.search.service;

import org.jnyou.common.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 *
 * @ClassName ElasticSaveService
 * @Author: JnYou
 **/
public interface ElasticSaveService {

    boolean productUp(List<SkuEsModel>skuEsModels) throws IOException;

}