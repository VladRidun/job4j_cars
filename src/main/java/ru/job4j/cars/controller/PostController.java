package ru.job4j.cars.controller;

import lombok.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.PostService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private PostService postService;

    @GetMapping("/{postId}")
    public String getPostById(Model model, @PathVariable int postId, HttpServletRequest request) {
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
        model.addAttribute("post",  postService.findAllOrderById());
        return "posts/list";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Post post, @ModelAttribute Car car, @ModelAttribute Engine engine, HttpSession session) {
        var user = (User) session.getAttribute("user");
        post.setUser(user);
        post.setCar(car);
        postService.create(post);
        return "redirect:/posts";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model, HttpSession session) {
        var user = (User) session.getAttribute("user");
        return "posts/create";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        postService.delete(id);
        return "redirect:/posts";
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(@PathVariable int id, Model model) {
        var postOptional = postService.findById(id);
        if (postOptional.isEmpty()) {
            model.addAttribute("message", "Задание не найдено");
            return "errors/404";
        }
        model.addAttribute("post", postOptional.get());
        return "posts/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Post post, HttpSession session, @RequestParam List<Integer> categoriesId) {
        var user = (User) session.getAttribute("user");
        post.setUser(user);
        postService.update(post);
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
    public String getPageTaskIsDone(Model model, @PathVariable int id) {
        var taskOptional = postService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Задание не найдено");
            return "errors/404";
        }
        var postDto = taskOptional.get();
        postService.update(postDto);
        return "redirect:/posts/completed";
    }
}
