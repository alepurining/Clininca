package com.clinica.clinica.web;

import com.clinica.clinica.domain.Usuario;
import com.clinica.clinica.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    private final UsuarioService usuarioService;

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login") // Muestra el formulario de login
    public String login(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @PostMapping("/login") // Recibe el correo y la contraseña del formulario
    public String iniciarSesion(
            @ModelAttribute("loginForm") LoginForm form,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        if (form.getCorreo().isBlank() || form.getPassword().isBlank()) {
            redirectAttributes.addFlashAttribute("error", "Ingresa tu correo y tu contraseña.");
            return "redirect:/login";
        }

        try {
            Usuario usuario = usuarioService.login(form.getCorreo(), form.getPassword());
            session.setAttribute("usuarioNombre", usuario.getNombre()); // Guarda quién inició sesión
            redirectAttributes.addFlashAttribute("mensaje", "Bienvenido, " + usuario.getNombre() + ".");
            return "redirect:/";
        } catch (IllegalArgumentException exception) { // Correo inexistente o contraseña errónea
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
            return "redirect:/login";
        }
    }
}
