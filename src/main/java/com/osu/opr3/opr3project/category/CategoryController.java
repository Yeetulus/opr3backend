package com.osu.opr3.opr3project.category;

import com.osu.opr3.opr3project.user.UserUtil;
import com.osu.opr3.opr3project.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@NonNull HttpServletRequest request,
                                                   @Valid @RequestBody CategoryRequest categoryRequest) throws IOException {
        return ResponseEntity.ok(categoryService.createCategory(UserUtil.castToUser(request.getAttribute("jwtUser")), categoryRequest));
    }

    @GetMapping("/get")
    @PreAuthorize("@categoryService.hasUserCategory(#id, @UserUtil.castToUser(#request.getAttribute('jwtUser')))")
    public ResponseEntity<Category> getUserCategory(@NonNull HttpServletRequest request,
                                                    @RequestParam Long id){
        return ResponseEntity.ok(categoryService.getCategory(id));
    }

    @PutMapping("/edit")
    @PreAuthorize("@categoryService.hasUserCategory(#id, @UserUtil.castToUser(#request.getAttribute('jwtUser')))")
    public ResponseEntity<Category> editUserCategory(@NonNull HttpServletRequest request,
                                                     @RequestParam Long id,
                                                     @RequestBody CategoryRequest categoryRequest){
        return ResponseEntity.ok(categoryService.editCategory(id, categoryRequest));
    }

    @DeleteMapping("/delete")
    @PreAuthorize("@categoryService.hasUserCategory(#id, @UserUtil.castToUser(#request.getAttribute('jwtUser')))")
    public ResponseEntity<Boolean> deleteUserCategory(@NonNull HttpServletRequest request,
                                                     @RequestParam Long id){
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Category>> getAllUserCategories(@NonNull HttpServletRequest request) throws IOException {
        return ResponseEntity.ok(categoryService.getAllUserCategories(UserUtil.castToUser(request.getAttribute("jwtUser"))));
    }
}
