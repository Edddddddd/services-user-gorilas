package cl.gorilas.user.web.rest;
import cl.gorilas.user.domain.Usuarios;
import cl.gorilas.user.repository.UsuariosRepository;
import cl.gorilas.user.web.rest.errors.BadRequestAlertException;
import cl.gorilas.user.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Usuarios.
 */
@RestController
@RequestMapping("/api")
public class UsuariosResource {

    private final Logger log = LoggerFactory.getLogger(UsuariosResource.class);

    private static final String ENTITY_NAME = "serviceUserGorilasUsuarios";

    private final UsuariosRepository usuariosRepository;

    public UsuariosResource(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    /**
     * POST  /usuarios : Create a new usuarios.
     *
     * @param usuarios the usuarios to create
     * @return the ResponseEntity with status 201 (Created) and with body the new usuarios, or with status 400 (Bad Request) if the usuarios has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/usuarios")
    public ResponseEntity<Usuarios> createUsuarios(@RequestBody Usuarios usuarios) throws URISyntaxException {
        log.debug("REST request to save Usuarios : {}", usuarios);
        if (usuarios.getId() != null) {
            throw new BadRequestAlertException("A new usuarios cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Usuarios result = usuariosRepository.save(usuarios);
        return ResponseEntity.created(new URI("/api/usuarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /usuarios : Updates an existing usuarios.
     *
     * @param usuarios the usuarios to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated usuarios,
     * or with status 400 (Bad Request) if the usuarios is not valid,
     * or with status 500 (Internal Server Error) if the usuarios couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/usuarios")
    public ResponseEntity<Usuarios> updateUsuarios(@RequestBody Usuarios usuarios) throws URISyntaxException {
        log.debug("REST request to update Usuarios : {}", usuarios);
        if (usuarios.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Usuarios result = usuariosRepository.save(usuarios);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, usuarios.getId().toString()))
            .body(result);
    }

    /**
     * GET  /usuarios : get all the usuarios.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of usuarios in body
     */
    @GetMapping("/usuarios")
    public List<Usuarios> getAllUsuarios() {
        log.debug("REST request to get all Usuarios");
        return usuariosRepository.findAll();
    }

    /**
     * GET  /usuarios/:id : get the "id" usuarios.
     *
     * @param id the id of the usuarios to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the usuarios, or with status 404 (Not Found)
     */
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuarios> getUsuarios(@PathVariable String id) {
        log.debug("REST request to get Usuarios : {}", id);
        Optional<Usuarios> usuarios = usuariosRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(usuarios);
    }

    /**
     * DELETE  /usuarios/:id : delete the "id" usuarios.
     *
     * @param id the id of the usuarios to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> deleteUsuarios(@PathVariable String id) {
        log.debug("REST request to delete Usuarios : {}", id);
        usuariosRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
