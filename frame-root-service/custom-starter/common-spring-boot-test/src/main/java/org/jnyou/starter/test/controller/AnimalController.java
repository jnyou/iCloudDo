package org.jnyou.starter.test.controller;

import org.jnyou.starter.autoconfigure.demo.AnimalService;
import org.jnyou.starter.autoconfigure.demo.BirdService;
import org.jnyou.starter.autoconfigure.demo.FishService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * 测试demo
 *
 * @author Jnyou
 * @version 1.0.0
 */
@RestController
@RequestMapping("/animal")
public class AnimalController {

    private final AnimalService animalService;

    private final BirdService birdService;

    private final FishService fishService;

    public AnimalController(AnimalService animalService, BirdService birdService, FishService fishService) {
        this.animalService = animalService;
        this.birdService = birdService;
        this.fishService = fishService;
    }

    @GetMapping("/")
    public String animal() {
        animalService.doing();
        return "animal";
    }

    @GetMapping("/fish")
    public String fish() {
        fishService.doing();
        return "fish";
    }

    @GetMapping("/bird")
    public String bird() {
        birdService.doing();
        return "bird";
    }
}
