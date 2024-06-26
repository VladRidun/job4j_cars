package ru.job4j.cars.controller;

import lombok.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.dto.AutoPhotoDto;
import ru.job4j.cars.model.*;
import ru.job4j.cars.service.AutoPhotoService;
import ru.job4j.cars.service.BrandService;
import ru.job4j.cars.service.ModelService;
import ru.job4j.cars.service.PostService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@AllArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final AutoPhotoService photoService;
    private final BrandService brandService;
    private final ModelService modelService;

    @GetMapping("/{postId}")
    public String getPostById(Model model, @PathVariable Long postId, HttpServletRequest request) {
        var postOptional = postService.findById(postId);
        if (postOptional.isEmpty()) {
            model.addAttribute("message", "Объявление не найдено");
            return "errors/404";
        }
        model.addAttribute("post", postOptional.get());
        var session = request.getSession();
        session.setAttribute("post", postOptional.get());
        return "posts/one";
    }

    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("posts", postService.findAllOrderById());
        return "posts/list";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Post post, @ModelAttribute Car car, @RequestParam MultipartFile file, HttpSession session) throws IOException {
        var user = (User) session.getAttribute("user");
        post.setUser(user);
        post.setCar(car);
        postService.create(post, new AutoPhotoDto(file.getOriginalFilename(), file.getBytes()));
        return "redirect:/posts";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model, HttpSession session) {
        var user = (User) session.getAttribute("user");
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("models", modelService.findAll());
        model.addAttribute("engines", Engine.values());
        model.addAttribute("transmissions", Transmission.values());
        model.addAttribute("bodyTypes", BodyType.values());
        model.addAttribute("colours", Colour.values());
        return "posts/create";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        postService.delete(id);
        return "redirect:/posts";
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(@PathVariable Long id, Model model) {
        var postOptional = postService.findById(id);
        var file = postOptional.get().getAutoPhoto().getId();
        if (postOptional.isEmpty()) {
            model.addAttribute("message", "Объявление не найдено");
            return "errors/404";
        }
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("models", modelService.findAll());
        model.addAttribute("engines", Engine.values());
        model.addAttribute("transmissions", Transmission.values());
        model.addAttribute("bodyTypes", BodyType.values());
        model.addAttribute("colours", Colour.values());
        model.addAttribute("post", postOptional.get());
        model.addAttribute("file", postOptional.get());
        return "posts/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Post post, @RequestParam MultipartFile file, HttpSession session) throws IOException {
        var user = (User) session.getAttribute("user");
        post.setUser(user);
        if ((!file.isEmpty())) {
            postService.update(post, new AutoPhotoDto(file.getOriginalFilename(), file.getBytes()));
        } else {
            postService.update(post);
        }
        return "redirect:/posts";
    }

    @GetMapping("/completed")
    public String getPageTasksIsDone(Model model, HttpSession session) {
        var user = (User) session.getAttribute("user");
        return "posts/completed";
    }

    @GetMapping("/unfulfilled")
    public String getPageTasksNotDone(Model model, HttpSession session) {
        var user = (User) session.getAttribute("user");
        return "posts/unfulfilled";
    }

    @GetMapping("/done/{id}")
    public String getPageTaskIsDone(Model model, @RequestParam MultipartFile file, @PathVariable Long id) throws IOException {
        var taskOptional = postService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Объявление не найдено");
            return "errors/404";
        }
        var postDto = taskOptional.get();
        postService.update(postDto, new AutoPhotoDto(file.getOriginalFilename(), file.getBytes()));
        return "redirect:/posts/completed";
    }
}
