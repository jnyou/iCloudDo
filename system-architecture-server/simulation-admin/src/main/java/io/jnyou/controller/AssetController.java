package io.jnyou.controller;

import io.jnyou.global.R;
import io.jnyou.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * <p>
 *
 * @className AssetController
 * @author: youjiannan xiaojian19970910@gmail.com
 **/
@RestController
public class AssetController {

    @Autowired
    AssetService assetService;

    @GetMapping("/assets")
    R listAssets(){
        return R.ok().setData(assetService.listAssets());
    }

    @GetMapping("/assets/tree/{integers}")
    R listAssets(@PathVariable("integers") Set<Integer> integers){
        return R.ok().setData(assetService.listChildrenTree(integers));
    }

//    @GetMapping("/assets/simpleTree/{integers}")
//    R simpleListAssets(@PathVariable("integers") Set<Integer> integers){
//        return R.ok().setData(assetService.simpleListAssets(integers));
//    }

    @GetMapping("/asset/{id}")
    R getAssetById(@PathVariable("id") Integer id) {
        return R.ok().setData(assetService.findAsset(id));
    }

}