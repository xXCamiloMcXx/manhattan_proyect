// --- Selección de Elementos del DOM ---
// Se guardan en constantes los elementos que se manipularán.
const navLinks = document.querySelectorAll('nav a.nav-link'); // Todos los enlaces de navegación
const sections = document.querySelectorAll('main section');    // Todas las secciones del <main>
const buyNowBtn = document.getElementById('btnBuyNow');       // El botón "Comprar ahora" del hero
const baseURL = 'http://localhost:8080';

/**
 * --- Función para Mostrar Secciones ---
 * Oculta todas las secciones y muestra solo la que corresponde al 'id' proporcionado.
 * También actualiza el estado 'active' en los enlaces de navegación.
 * @param {string} id - El ID de la sección a mostrar (ej. 'home', 'buy').
 */
function showSection(id) {
  // Oculta todas las secciones y quita sus atributos de accesibilidad.
  sections.forEach(sec => {
    if (sec.id === id) {
      sec.classList.add('active');
      sec.style.display = 'block'; // Muestra la sección deseada.
      sec.setAttribute('tabindex', '0'); // La hace enfocable.
      sec.focus(); // Pone el foco en la nueva sección.
    } else {
      sec.classList.remove('active');
      sec.style.display = 'none'; // Oculta las demás secciones.
      sec.removeAttribute('tabindex');
    }
  });

  // Actualiza el estilo del enlace de navegación activo.
  navLinks.forEach(link => {
    if (link.dataset.target === id) {
      link.classList.add('active');
      link.setAttribute('aria-current', 'page'); // Indica la página actual para accesibilidad.
    } else {
      link.classList.remove('active');
      link.removeAttribute('aria-current');
    }
  });
}

// --- Event Listeners para la Navegación ---
// Añade un evento a cada enlace de navegación.
navLinks.forEach(link => {
  // Evento para clics del ratón.
  link.addEventListener('click', (e) => {
    e.preventDefault(); // Evita que el navegador recargue la página.
    const targetId = link.dataset.target; // Obtiene el ID de la sección desde el atributo 'data-target'.
    showSection(targetId); // Muestra la sección correspondiente.
  });

  // Evento para accesibilidad con teclado (Enter o Espacio).
  link.addEventListener('keydown', (e) => {
    if (e.key === 'Enter' || e.key === ' ') {
      e.preventDefault();
      const targetId = link.dataset.target;
      showSection(targetId);
    }
  });
});

// Evento para el botón "Comprar ahora".
buyNowBtn.addEventListener('click', () => {
  showSection('buy'); // Muestra directamente la sección de compra.
});

// --- Manejo de Formularios ---

// 1. Formulario de Compra
document.getElementById('buyForm').addEventListener('submit', (e) => {
  e.preventDefault(); // Previene el envío real del formulario.
  const eventName = document.getElementById('eventSelect').value;
  const quantity = document.getElementById('ticketQuantity').value;

  if (!eventName) {
    alert('Por favor selecciona un evento.');
    return; // Detiene la ejecución si no se selecciona un evento.
  }
  // Muestra una confirmación de la compra.
  alert(`¡Gracias por comprar ${quantity} entradas para ${eventName}!`);
  e.target.reset(); // Limpia los campos del formulario.
  showSection('home'); // Vuelve a la página de inicio.
});

// 2. Formulario de Contacto
document.getElementById('contactForm').addEventListener('submit', (e) => {
  e.preventDefault();
  alert('Gracias por contactarnos. Responderemos pronto a tu mensaje.');
  e.target.reset();
  showSection('home');
});

// 3. Formulario de Registro
document.getElementById('registerForm').addEventListener('submit', async (e) => {
  e.preventDefault();
  const email = e.target['correo'].value;
  const pass1 = e.target['contrasena'].value;
  const pass2 = e.target['contrasena_confirm'].value;
  const nombre = e.target['nombre'].value;
  const apellido = e.target['apellido'].value;
  const cedula = e.target['cedula'].value;
  const fechaDeNacimiento = e.target['fecha_nacimiento'].value;
  const telefono = e.target['numero_celular'].value;
  const paisDeResidencia = e.target['pais_de_residencia'].value;
  const genero = e.target['genero'].value;
  const perfil = e.target['perfil'].value;

  // Comprueba si las contraseñas coinciden.
  if (pass1 !== pass2) {
    alert('Las contraseñas no coinciden, intenta nuevamente.');
    return;
  }

  try {
    const response = await fetch(`${baseURL}/usuarios`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({email ,password: pass1, nombre, apellido, cedula, fechaDeNacimiento, telefono, paisDeResidencia, genero, perfil})
    });
    //const data = await response.json();

    if (response.ok){
      alert('Creada correctamente');
      e.target.reset();
    }else{
      alert('algo ocurrio no fue creada la cuenta');
      e.target.reset();
    }
    
    /*if (response.ok) {
      alert(data.message);
      e.target.reset();
      showSection('login');
    } else {
      alert('Error: ' + data.message);
    }*/
  } catch (error) {
    alert('Error al conectar: ' + error.message);
  }
});

// 4. Formulario de Inicio de Sesión
document.getElementById('loginForm').addEventListener('submit', async (e) => {
  e.preventDefault();
  const email = e.target['correo'].value;
  const contrasena = e.target['contrasena'].value;

  try {
    const response = await fetch(`${baseURL}/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({email,contrasena}),
    });
    const data = await response.json();
    
    if (response.ok) {
      alert(data.message);
      localStorage.setItem('token', data.tokenJWT);
      e.target.reset();
      showSection('home');
    } else {
      alert('Error: ' + data.message);
    }
  } catch (error) {
    alert('Error al conectar: ' + error.message);
  }
});

/*document.getElementById('loginForm').addEventListener('submit', async (e) => {
  e.preventDefault();
  const email = e.target['correo'].value;
  const contrasena = e.target['contrasena'].value;

  try {
    const response = await fetch(`${baseURL}/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({email,contrasena})
    });

    if (response.ok){
      alert('Logeado correctamente');
      e.target.reset();
    }else{
      alert('no es tu cuenta');
      e.target.reset();
    }
    
  } catch (error) {
    alert('Error al conectar: ');
  }
});*/