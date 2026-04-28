package com.ingeniawork.ticketflow.controller;


import com.ingeniawork.ticketflow.domain.boleta.Boleta;
import com.ingeniawork.ticketflow.domain.boleta.DatosCreacionBoleta;
import com.ingeniawork.ticketflow.domain.boleta.DatosDetalleBoleta;
import com.ingeniawork.ticketflow.domain.evento.DatosDetalleEvento;
import com.ingeniawork.ticketflow.domain.evento.DatosRegistroEvento;
import com.ingeniawork.ticketflow.domain.evento.Evento;
import com.ingeniawork.ticketflow.repository.BoletaRepository;
import com.ingeniawork.ticketflow.repository.EventoRepository;
import com.ingeniawork.ticketflow.repository.TipoDeBoletaRepository;
import com.ingeniawork.ticketflow.repository.UsuarioRepository;
import com.ingeniawork.ticketflow.service.QRCodeService;
import com.ingeniawork.ticketflow.service.TokenService;
import com.ingeniawork.ticketflow.service.TokenUUIDService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boletas")
public class BoletaController {

    @Autowired
    BoletaRepository boletaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TipoDeBoletaRepository tipoDeBoletaRepository;

    @Autowired
    EventoRepository eventoRepository;

    @Autowired
    TokenUUIDService tokenUUIDService;

    @Autowired
    QRCodeService qrCodeService;

    @Transactional
    @PostMapping
    public ResponseEntity registrar(@RequestBody @Valid DatosCreacionBoleta datosCreacionBoleta){
        var usuario = usuarioRepository.getReferenceById(datosCreacionBoleta.usuarioId());
        var tipoDeBoleta = tipoDeBoletaRepository.getReferenceById(datosCreacionBoleta.tipoDeBoletaId());
        var evento = eventoRepository.getReferenceById(tipoDeBoleta.getEvento().getId());
        var token = tokenUUIDService.GenerarToken();
        var boleta = new Boleta(token,usuario,tipoDeBoleta,evento);
        boletaRepository.save(boleta);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> descargarBoleta (@PathVariable Long id){
        var boleta = boletaRepository.getReferenceById(id);
        byte[] pdf = qrCodeService.generarPDF(boleta.getToken());
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=boleta_" + id + ".pdf")
                .body(pdf);
    }

    /*@GetMapping("/v1/qrcode")
    public void generateQRCode(HttpServletResponse response,
                               @RequestParam String text,
                               @RequestParam(defaultValue = "350") int width,
                               @RequestParam(defaultValue = "350") int height) throws Exception {
        BufferedImage image = qrCodeService.generateQRCode(text, width, height);
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "png", outputStream);
        outputStream.flush();
        outputStream.close();
    }*/

}
