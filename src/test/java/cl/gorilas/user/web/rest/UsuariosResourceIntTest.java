package cl.gorilas.user.web.rest;

import cl.gorilas.user.ServiceUserGorilasApp;

import cl.gorilas.user.domain.Usuarios;
import cl.gorilas.user.repository.UsuariosRepository;
import cl.gorilas.user.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static cl.gorilas.user.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UsuariosResource REST controller.
 *
 * @see UsuariosResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceUserGorilasApp.class)
public class UsuariosResourceIntTest {

    private static final String DEFAULT_NOMBRE_COMPLETO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_COMPLETO = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO_ELECTRONICO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO_ELECTRONICO = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTO = "BBBBBBBBBB";

    private static final Long DEFAULT_ABONO = 1L;
    private static final Long UPDATED_ABONO = 2L;

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final Integer DEFAULT_TURNOS_DISPONIBLES = 1;
    private static final Integer UPDATED_TURNOS_DISPONIBLES = 2;

    private static final String DEFAULT_TURNOS_DISPONIBLES_LEYENDA = "AAAAAAAAAA";
    private static final String UPDATED_TURNOS_DISPONIBLES_LEYENDA = "BBBBBBBBBB";

    private static final String DEFAULT_TURNOS_DISPONIBLES_LEYENDA_HTML = "AAAAAAAAAA";
    private static final String UPDATED_TURNOS_DISPONIBLES_LEYENDA_HTML = "BBBBBBBBBB";

    private static final String DEFAULT_ABONOS_VENCIMIENTO_HTML = "AAAAAAAAAA";
    private static final String UPDATED_ABONOS_VENCIMIENTO_HTML = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_PROX_APTO_FISICO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_PROX_APTO_FISICO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_PROX_APTO_FISICO_STRING = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_PROX_APTO_FISICO_STRING = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Float DEFAULT_DIAS_RESTANTES = 1F;
    private static final Float UPDATED_DIAS_RESTANTES = 2F;

    private static final Boolean DEFAULT_TIENE_DEUDA = false;
    private static final Boolean UPDATED_TIENE_DEUDA = true;

    private static final Integer DEFAULT_SALDO = 1;
    private static final Integer UPDATED_SALDO = 2;

    private static final String DEFAULT_DOMICILIO = "AAAAAAAAAA";
    private static final String UPDATED_DOMICILIO = "BBBBBBBBBB";

    private static final String DEFAULT_BARRIO = "AAAAAAAAAA";
    private static final String UPDATED_BARRIO = "BBBBBBBBBB";

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restUsuariosMockMvc;

    private Usuarios usuarios;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UsuariosResource usuariosResource = new UsuariosResource(usuariosRepository);
        this.restUsuariosMockMvc = MockMvcBuilders.standaloneSetup(usuariosResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Usuarios createEntity() {
        Usuarios usuarios = new Usuarios()
            .nombreCompleto(DEFAULT_NOMBRE_COMPLETO)
            .correoElectronico(DEFAULT_CORREO_ELECTRONICO)
            .documento(DEFAULT_DOCUMENTO)
            .abono(DEFAULT_ABONO)
            .estado(DEFAULT_ESTADO)
            .turnosDisponibles(DEFAULT_TURNOS_DISPONIBLES)
            .turnosDisponiblesLeyenda(DEFAULT_TURNOS_DISPONIBLES_LEYENDA)
            .turnosDisponiblesLeyendaHtml(DEFAULT_TURNOS_DISPONIBLES_LEYENDA_HTML)
            .abonosVencimientoHtml(DEFAULT_ABONOS_VENCIMIENTO_HTML)
            .fechaProxAptoFisico(DEFAULT_FECHA_PROX_APTO_FISICO)
            .fechaProxAptoFisicoString(DEFAULT_FECHA_PROX_APTO_FISICO_STRING)
            .diasRestantes(DEFAULT_DIAS_RESTANTES)
            .tieneDeuda(DEFAULT_TIENE_DEUDA)
            .saldo(DEFAULT_SALDO)
            .domicilio(DEFAULT_DOMICILIO)
            .barrio(DEFAULT_BARRIO);
        return usuarios;
    }

    @Before
    public void initTest() {
        usuariosRepository.deleteAll();
        usuarios = createEntity();
    }

    @Test
    public void createUsuarios() throws Exception {
        int databaseSizeBeforeCreate = usuariosRepository.findAll().size();

        // Create the Usuarios
        restUsuariosMockMvc.perform(post("/api/usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usuarios)))
            .andExpect(status().isCreated());

        // Validate the Usuarios in the database
        List<Usuarios> usuariosList = usuariosRepository.findAll();
        assertThat(usuariosList).hasSize(databaseSizeBeforeCreate + 1);
        Usuarios testUsuarios = usuariosList.get(usuariosList.size() - 1);
        assertThat(testUsuarios.getNombreCompleto()).isEqualTo(DEFAULT_NOMBRE_COMPLETO);
        assertThat(testUsuarios.getCorreoElectronico()).isEqualTo(DEFAULT_CORREO_ELECTRONICO);
        assertThat(testUsuarios.getDocumento()).isEqualTo(DEFAULT_DOCUMENTO);
        assertThat(testUsuarios.getAbono()).isEqualTo(DEFAULT_ABONO);
        assertThat(testUsuarios.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testUsuarios.getTurnosDisponibles()).isEqualTo(DEFAULT_TURNOS_DISPONIBLES);
        assertThat(testUsuarios.getTurnosDisponiblesLeyenda()).isEqualTo(DEFAULT_TURNOS_DISPONIBLES_LEYENDA);
        assertThat(testUsuarios.getTurnosDisponiblesLeyendaHtml()).isEqualTo(DEFAULT_TURNOS_DISPONIBLES_LEYENDA_HTML);
        assertThat(testUsuarios.getAbonosVencimientoHtml()).isEqualTo(DEFAULT_ABONOS_VENCIMIENTO_HTML);
        assertThat(testUsuarios.getFechaProxAptoFisico()).isEqualTo(DEFAULT_FECHA_PROX_APTO_FISICO);
        assertThat(testUsuarios.getFechaProxAptoFisicoString()).isEqualTo(DEFAULT_FECHA_PROX_APTO_FISICO_STRING);
        assertThat(testUsuarios.getDiasRestantes()).isEqualTo(DEFAULT_DIAS_RESTANTES);
        assertThat(testUsuarios.isTieneDeuda()).isEqualTo(DEFAULT_TIENE_DEUDA);
        assertThat(testUsuarios.getSaldo()).isEqualTo(DEFAULT_SALDO);
        assertThat(testUsuarios.getDomicilio()).isEqualTo(DEFAULT_DOMICILIO);
        assertThat(testUsuarios.getBarrio()).isEqualTo(DEFAULT_BARRIO);
    }

    @Test
    public void createUsuariosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = usuariosRepository.findAll().size();

        // Create the Usuarios with an existing ID
        usuarios.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsuariosMockMvc.perform(post("/api/usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usuarios)))
            .andExpect(status().isBadRequest());

        // Validate the Usuarios in the database
        List<Usuarios> usuariosList = usuariosRepository.findAll();
        assertThat(usuariosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllUsuarios() throws Exception {
        // Initialize the database
        usuariosRepository.save(usuarios);

        // Get all the usuariosList
        restUsuariosMockMvc.perform(get("/api/usuarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuarios.getId())))
            .andExpect(jsonPath("$.[*].nombreCompleto").value(hasItem(DEFAULT_NOMBRE_COMPLETO.toString())))
            .andExpect(jsonPath("$.[*].correoElectronico").value(hasItem(DEFAULT_CORREO_ELECTRONICO.toString())))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(DEFAULT_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].abono").value(hasItem(DEFAULT_ABONO.intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].turnosDisponibles").value(hasItem(DEFAULT_TURNOS_DISPONIBLES)))
            .andExpect(jsonPath("$.[*].turnosDisponiblesLeyenda").value(hasItem(DEFAULT_TURNOS_DISPONIBLES_LEYENDA.toString())))
            .andExpect(jsonPath("$.[*].turnosDisponiblesLeyendaHtml").value(hasItem(DEFAULT_TURNOS_DISPONIBLES_LEYENDA_HTML.toString())))
            .andExpect(jsonPath("$.[*].abonosVencimientoHtml").value(hasItem(DEFAULT_ABONOS_VENCIMIENTO_HTML.toString())))
            .andExpect(jsonPath("$.[*].fechaProxAptoFisico").value(hasItem(DEFAULT_FECHA_PROX_APTO_FISICO.toString())))
            .andExpect(jsonPath("$.[*].fechaProxAptoFisicoString").value(hasItem(DEFAULT_FECHA_PROX_APTO_FISICO_STRING.toString())))
            .andExpect(jsonPath("$.[*].diasRestantes").value(hasItem(DEFAULT_DIAS_RESTANTES.doubleValue())))
            .andExpect(jsonPath("$.[*].tieneDeuda").value(hasItem(DEFAULT_TIENE_DEUDA.booleanValue())))
            .andExpect(jsonPath("$.[*].saldo").value(hasItem(DEFAULT_SALDO)))
            .andExpect(jsonPath("$.[*].domicilio").value(hasItem(DEFAULT_DOMICILIO.toString())))
            .andExpect(jsonPath("$.[*].barrio").value(hasItem(DEFAULT_BARRIO.toString())));
    }
    
    @Test
    public void getUsuarios() throws Exception {
        // Initialize the database
        usuariosRepository.save(usuarios);

        // Get the usuarios
        restUsuariosMockMvc.perform(get("/api/usuarios/{id}", usuarios.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(usuarios.getId()))
            .andExpect(jsonPath("$.nombreCompleto").value(DEFAULT_NOMBRE_COMPLETO.toString()))
            .andExpect(jsonPath("$.correoElectronico").value(DEFAULT_CORREO_ELECTRONICO.toString()))
            .andExpect(jsonPath("$.documento").value(DEFAULT_DOCUMENTO.toString()))
            .andExpect(jsonPath("$.abono").value(DEFAULT_ABONO.intValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.turnosDisponibles").value(DEFAULT_TURNOS_DISPONIBLES))
            .andExpect(jsonPath("$.turnosDisponiblesLeyenda").value(DEFAULT_TURNOS_DISPONIBLES_LEYENDA.toString()))
            .andExpect(jsonPath("$.turnosDisponiblesLeyendaHtml").value(DEFAULT_TURNOS_DISPONIBLES_LEYENDA_HTML.toString()))
            .andExpect(jsonPath("$.abonosVencimientoHtml").value(DEFAULT_ABONOS_VENCIMIENTO_HTML.toString()))
            .andExpect(jsonPath("$.fechaProxAptoFisico").value(DEFAULT_FECHA_PROX_APTO_FISICO.toString()))
            .andExpect(jsonPath("$.fechaProxAptoFisicoString").value(DEFAULT_FECHA_PROX_APTO_FISICO_STRING.toString()))
            .andExpect(jsonPath("$.diasRestantes").value(DEFAULT_DIAS_RESTANTES.doubleValue()))
            .andExpect(jsonPath("$.tieneDeuda").value(DEFAULT_TIENE_DEUDA.booleanValue()))
            .andExpect(jsonPath("$.saldo").value(DEFAULT_SALDO))
            .andExpect(jsonPath("$.domicilio").value(DEFAULT_DOMICILIO.toString()))
            .andExpect(jsonPath("$.barrio").value(DEFAULT_BARRIO.toString()));
    }

    @Test
    public void getNonExistingUsuarios() throws Exception {
        // Get the usuarios
        restUsuariosMockMvc.perform(get("/api/usuarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateUsuarios() throws Exception {
        // Initialize the database
        usuariosRepository.save(usuarios);

        int databaseSizeBeforeUpdate = usuariosRepository.findAll().size();

        // Update the usuarios
        Usuarios updatedUsuarios = usuariosRepository.findById(usuarios.getId()).get();
        updatedUsuarios
            .nombreCompleto(UPDATED_NOMBRE_COMPLETO)
            .correoElectronico(UPDATED_CORREO_ELECTRONICO)
            .documento(UPDATED_DOCUMENTO)
            .abono(UPDATED_ABONO)
            .estado(UPDATED_ESTADO)
            .turnosDisponibles(UPDATED_TURNOS_DISPONIBLES)
            .turnosDisponiblesLeyenda(UPDATED_TURNOS_DISPONIBLES_LEYENDA)
            .turnosDisponiblesLeyendaHtml(UPDATED_TURNOS_DISPONIBLES_LEYENDA_HTML)
            .abonosVencimientoHtml(UPDATED_ABONOS_VENCIMIENTO_HTML)
            .fechaProxAptoFisico(UPDATED_FECHA_PROX_APTO_FISICO)
            .fechaProxAptoFisicoString(UPDATED_FECHA_PROX_APTO_FISICO_STRING)
            .diasRestantes(UPDATED_DIAS_RESTANTES)
            .tieneDeuda(UPDATED_TIENE_DEUDA)
            .saldo(UPDATED_SALDO)
            .domicilio(UPDATED_DOMICILIO)
            .barrio(UPDATED_BARRIO);

        restUsuariosMockMvc.perform(put("/api/usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUsuarios)))
            .andExpect(status().isOk());

        // Validate the Usuarios in the database
        List<Usuarios> usuariosList = usuariosRepository.findAll();
        assertThat(usuariosList).hasSize(databaseSizeBeforeUpdate);
        Usuarios testUsuarios = usuariosList.get(usuariosList.size() - 1);
        assertThat(testUsuarios.getNombreCompleto()).isEqualTo(UPDATED_NOMBRE_COMPLETO);
        assertThat(testUsuarios.getCorreoElectronico()).isEqualTo(UPDATED_CORREO_ELECTRONICO);
        assertThat(testUsuarios.getDocumento()).isEqualTo(UPDATED_DOCUMENTO);
        assertThat(testUsuarios.getAbono()).isEqualTo(UPDATED_ABONO);
        assertThat(testUsuarios.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testUsuarios.getTurnosDisponibles()).isEqualTo(UPDATED_TURNOS_DISPONIBLES);
        assertThat(testUsuarios.getTurnosDisponiblesLeyenda()).isEqualTo(UPDATED_TURNOS_DISPONIBLES_LEYENDA);
        assertThat(testUsuarios.getTurnosDisponiblesLeyendaHtml()).isEqualTo(UPDATED_TURNOS_DISPONIBLES_LEYENDA_HTML);
        assertThat(testUsuarios.getAbonosVencimientoHtml()).isEqualTo(UPDATED_ABONOS_VENCIMIENTO_HTML);
        assertThat(testUsuarios.getFechaProxAptoFisico()).isEqualTo(UPDATED_FECHA_PROX_APTO_FISICO);
        assertThat(testUsuarios.getFechaProxAptoFisicoString()).isEqualTo(UPDATED_FECHA_PROX_APTO_FISICO_STRING);
        assertThat(testUsuarios.getDiasRestantes()).isEqualTo(UPDATED_DIAS_RESTANTES);
        assertThat(testUsuarios.isTieneDeuda()).isEqualTo(UPDATED_TIENE_DEUDA);
        assertThat(testUsuarios.getSaldo()).isEqualTo(UPDATED_SALDO);
        assertThat(testUsuarios.getDomicilio()).isEqualTo(UPDATED_DOMICILIO);
        assertThat(testUsuarios.getBarrio()).isEqualTo(UPDATED_BARRIO);
    }

    @Test
    public void updateNonExistingUsuarios() throws Exception {
        int databaseSizeBeforeUpdate = usuariosRepository.findAll().size();

        // Create the Usuarios

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuariosMockMvc.perform(put("/api/usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usuarios)))
            .andExpect(status().isBadRequest());

        // Validate the Usuarios in the database
        List<Usuarios> usuariosList = usuariosRepository.findAll();
        assertThat(usuariosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteUsuarios() throws Exception {
        // Initialize the database
        usuariosRepository.save(usuarios);

        int databaseSizeBeforeDelete = usuariosRepository.findAll().size();

        // Delete the usuarios
        restUsuariosMockMvc.perform(delete("/api/usuarios/{id}", usuarios.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Usuarios> usuariosList = usuariosRepository.findAll();
        assertThat(usuariosList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Usuarios.class);
        Usuarios usuarios1 = new Usuarios();
        usuarios1.setId("id1");
        Usuarios usuarios2 = new Usuarios();
        usuarios2.setId(usuarios1.getId());
        assertThat(usuarios1).isEqualTo(usuarios2);
        usuarios2.setId("id2");
        assertThat(usuarios1).isNotEqualTo(usuarios2);
        usuarios1.setId(null);
        assertThat(usuarios1).isNotEqualTo(usuarios2);
    }
}
