package com.clinica.clinica.web;

import com.clinica.clinica.domain.enums.NivelRiesgo;
import com.clinica.clinica.service.DocumentoDuplicadoException;
import com.clinica.clinica.service.PacienteService;
import com.clinica.clinica.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PacienteController { //Se utiliza para indicar que esta clase es un controlador de Spring MVC, y que manejará las solicitudes HTTP relacionadas con los pacientes.

    private final PacienteService pacienteService; //Se utiliza para indicar que esta clase tiene una dependencia de PacienteService, y que se inyectará automáticamente mediante el constructor.
    private final UsuarioService usuarioService;

    public PacienteController(PacienteService pacienteService, UsuarioService usuarioService) {
        this.pacienteService = pacienteService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/") //Se utiliza para indicar que este método se ejecutará cuando se haga una petición GET a la ruta "/"
    public String inicio(Model model) {
        model.addAttribute("pacientes", pacienteService.listar()); //Se utiliza para indicar que queremos agregar un atributo al modelo, que será una lista de pacientes obtenida del servicio pacienteService, y que estará disponible en la vista index.html con el nombre "pacientes".
        model.addAttribute("nivelesRiesgo", NivelRiesgo.values());
        model.addAttribute("pacienteForm", new PacienteForm()); //Se utiliza para indicar que queremos agregar un atributo al modelo, que será un objeto de tipo PacienteForm, y que estará disponible en la vista index.html con el nombre "pacienteForm". Esto nos permite vincular los campos del formulario con las propiedades del objeto PacienteForm.
        model.addAttribute("recuperacionForm", new RecuperacionForm()); //Se utiliza para indicar que queremos agregar un atributo al modelo, que será un objeto de tipo RecuperacionForm, y que estará disponible en la vista recuperar.html con el nombre "recuperacionForm".
        return "index";
    }
    
    @PostMapping("/pacientes")
    public String registrar(
            @ModelAttribute("pacienteForm") PacienteForm form, //Se utiliza para indicar que queremos vincular los campos del formulario con las propiedades del objeto PacienteForm, y que este objeto estará disponible en el método como parámetro "form".
            RedirectAttributes redirectAttributes //Se utiliza para indicar que queremos redirigir a otra página después de procesar la solicitud, y que podemos agregar atributos al modelo que estarán disponibles en la página de destino.
    ) {
        if (form.getDocumento().isBlank() || form.getNombreCompleto().isBlank() //Se utiliza para indicar que queremos validar que los campos del formulario no estén vacíos, y si alguno de ellos lo está, redirigimos a la página de inicio con un mensaje de error.
            || form.getTelefono().isBlank()) { //Se utiliza para indicar que queremos validar que los campos del formulario no estén vacíos, y si alguno de ellos lo está, redirigimos a la página de inicio con un mensaje de error.
            redirectAttributes.addFlashAttribute("error", "Completa todos los campos."); //Se utiliza para indicar que queremos agregar un atributo al modelo que estará disponible en la página de destino, y que contendrá el mensaje de error "Completa todos los campos.".
            return "redirect:/";
        }

        try { //Se utiliza para indicar que queremos intentar registrar un nuevo paciente, y si ocurre una excepción de tipo DocumentoDuplicadoException, redirigimos a la página de inicio con un mensaje de error.
            pacienteService.registrar(
                    form.getDocumento(),
                    form.getNombreCompleto(),
                    form.getTelefono(),
                    form.getNivelRiesgo()
            );
            redirectAttributes.addFlashAttribute("mensaje", "Paciente registrado.");
        } catch (DocumentoDuplicadoException exception) { //Se utiliza para indicar que queremos capturar la excepción de tipo DocumentoDuplicadoException, y redirigimos a la página de inicio con un mensaje de error que contiene el mensaje de la excepción.
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
        }

        return "redirect:/"; //Se utiliza para indicar que queremos redirigir a la página de inicio después de procesar la solicitud, y que los atributos agregados al modelo estarán disponibles en esa página.
    }

    @GetMapping("/recuperar") //Se utiliza para indicar que este método se ejecutará cuando se haga una petición GET a la ruta "/recuperar"
    public String recuperar(Model model) { //Se utiliza para indicar que queremos agregar un atributo al modelo, que será un objeto de tipo RecuperacionForm, y que estará disponible en la vista recuperar.html con el nombre "recuperacionForm". Esto nos permite vincular los campos del formulario con las propiedades del objeto RecuperacionForm.
        model.addAttribute("recuperacionForm", new RecuperacionForm()); //Se utiliza para indicar que queremos agregar un atributo al modelo, que será un objeto de tipo RecuperacionForm, y que estará disponible en la vista recuperar.html con el nombre "recuperacionForm". Esto nos permite vincular los campos del formulario con las propiedades del objeto RecuperacionForm.
        return "recuperar";
    }

    @PostMapping("/recuperar") //Se utiliza para indicar que este método se ejecutará cuando se haga una petición POST a la ruta "/recuperar"
    public String solicitarRecuperacion(
            @ModelAttribute("recuperacionForm") RecuperacionForm form,
            RedirectAttributes redirectAttributes
    ) {
        if (form.getCorreo() == null || form.getCorreo().isBlank()) {
            redirectAttributes.addFlashAttribute("error", "Ingresa tu correo.");
            return "redirect:/recuperar";
        }

        usuarioService.solicitarRecuperacion(form.getCorreo());
        redirectAttributes.addFlashAttribute(
                "mensaje",
                "Si el correo está registrado, recibirás instrucciones."
        );
        return "redirect:/recuperar";
    }
}