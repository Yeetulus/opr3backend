package com.osu.opr3.opr3project.category;

import com.osu.opr3.opr3project.security.SecurityService;
import com.osu.opr3.opr3project.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final SecurityService securityService;
    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@NonNull HttpServletRequest request,
                                                   @Valid @RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok(categoryService.createCategory((User)request.getAttribute("jwtUser"), categoryRequest));
    }

    @GetMapping("/get")
    public ResponseEntity<Category> getUserCategory(@NonNull HttpServletRequest request,
                                                    @RequestParam Long id){
        securityService.hasUserCategory((User)request.getAttribute("jwtUser"), id);
        return ResponseEntity.ok(categoryService.getCategory(id));
    }

    @PutMapping("/edit")
    public ResponseEntity<Category> editUserCategory(@NonNull HttpServletRequest request,
                                                     @RequestParam Long id,
                                                     @Valid @RequestBody CategoryRequest categoryRequest){
        securityService.hasUserCategory((User)request.getAttribute("jwtUser"), id);
        return ResponseEntity.ok(categoryService.editCategory(id, categoryRequest));
    }

    @DeleteMapping("/delete")
    //@PreAuthorize("categoryService.hasUserCategory(#id, @UserUtil.castToUser(#request.getAttribute('jwtUser')))")
    public ResponseEntity<Boolean> deleteUserCategory(@NonNull HttpServletRequest request,
                                                     @RequestParam Long id){
        securityService.hasUserCategory((User)request.getAttribute("jwtUser"), id);
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Category>> getAllUserCategories(@NonNull HttpServletRequest request) {
        return ResponseEntity.ok(categoryService.getAllUserCategories((User)request.getAttribute("jwtUser")));
    }
}
