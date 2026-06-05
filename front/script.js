const navLinks =
  document.querySelectorAll('.nav-link');

const sections =
  document.querySelectorAll('main section');

const buyNowBtn =
  document.getElementById('btnBuyNow');

const themeToggle = document.getElementById('themeToggle');

const baseURL = 'http://localhost:8080';

let cart = [];

// SHOW SECTION
function showSection(id) {

  sections.forEach(sec => {

    if (sec.id === id) {

      sec.classList.add('active');

      sec.style.display = 'block';

    } else {

      sec.classList.remove('active');

      sec.style.display = 'none';
    }
  });

  navLinks.forEach(link => {

    if (link.dataset.target === id) {

      link.classList.add('active');

    } else {

      link.classList.remove('active');
    }
  });
}

// NAVIGATION
navLinks.forEach(link => {

  link.addEventListener('click', (e) => {

    e.preventDefault();

    showSection(
      link.dataset.target
    );
  });
});

// HERO BUTTON
buyNowBtn.addEventListener('click', () => {
  showSection('buy');
  cargarEventos();
});

// DARK MODE
themeToggle.addEventListener('click', () => {

  document.body.classList.toggle('dark');

  const isDark =
    document.body.classList.contains('dark');

  localStorage.setItem(
    'theme',
    isDark ? 'dark' : 'light'
  );

  themeToggle.textContent =
    isDark ? '☀️' : '🌙';
});

// LOAD THEME
function loadTheme() {

  const savedTheme =
    localStorage.getItem('theme');

  if (savedTheme === 'dark') {

    document.body.classList.add('dark');

    themeToggle.textContent = '☀️';
  }
}

// ADD TO CART
function addEventToCart(event, price) {

  cart.push({
    event,
    price
  });

  renderCart();

  alert('Evento agregado al carrito');
}

// CART COUNT
function updateCartCount() {

  document.getElementById('cartCount')
    .textContent = cart.length;
}

// RENDER CART
function renderCart() {

  const cartItems =
    document.getElementById('cartItems');

  const cartTotal =
    document.getElementById('cartTotal');

  if (!cartItems) return;

  cartItems.innerHTML = '';

  if (cart.length === 0) {

    cartItems.innerHTML = `
      <p>
        Tu carrito está vacío.
      </p>
    `;

    cartTotal.textContent = '$0';

    updateCartCount();

    return;
  }

  let total = 0;

  cart.forEach((item, index) => {

    total += item.price;

    const div =
      document.createElement('div');

    div.innerHTML = `

      <div
        style="
          display:flex;
          justify-content:space-between;
          align-items:center;
          background:var(--color-card);
          padding:1rem;
          border-radius:1rem;
          margin-bottom:1rem;
        "
      >

        <div>

          <h3>
            ${item.event}
          </h3>

          <p>
            1 boleto
          </p>

        </div>

        <div>

          <strong>
            $${item.price}
          </strong>

          <br><br>

          <button onclick="removeCartItem(${index})">
            Eliminar
          </button>

        </div>

      </div>

    `;

    cartItems.appendChild(div);
  });

  cartTotal.textContent = `$${total}`;

  updateCartCount();
}

// REMOVE
function removeCartItem(index) {

  cart.splice(index,1);

  renderCart();
}

// CHECKOUT
document.getElementById('checkoutBtn')?.addEventListener('click', async (e) => {
  e.preventDefault();
  const form = e.target;

  if (sessionStorage.getItem('token') != null){
    const shopCar = {
      "asiento":1,
      "tipoDeBoletaId":1
    };

    for(const element of cart) {

      const token = sessionStorage.getItem('token');
      const form = e.target;


      try {
        const response = await fetch(`${baseURL}/boletas`, {
          method: 'POST',
          headers: { 
            'Content-Type': 'application/json',
            'Authorization':`${token}`
          },
          body: JSON.stringify({
            asiento:shopCar.asiento,
            tipoDeBoletaId:shopCar.tipoDeBoletaId,
          })
        });
        //const data = await response.json();

        if (response.ok){
          alert('Compra realizada');
        }else{
          alert('algo ocurrio no fue realizada la compra');
          form.reset();
        }
      } catch (error) {
        alert('Error al conectar: ' + error.message);
      }

      shopCar.asiento ++;
      shopCar.tipoDeBoletaId++;

    };

    cart = [];
    renderCart();
    showSection('profile');
    cargarTickets();

  }else{
    alert('porfavor Inicia Sesion primero');
    showSection('login');
  }

});


// REGISTER
document.getElementById('registerForm').addEventListener('submit', async (e) => {  
  e.preventDefault();
  const form = e.target;
  const user = {
    email: e.target['correo'].value,
    contrasena: e.target['contrasena'].value,
    contrasenaConfirm: e.target['contrasena_confirm'].value,
    nombre: e.target['nombre'].value,
    apellido: e.target['apellido'].value,
    tipoDeDocumento: e.target['tipo_documento'].value,
    numeroDeDocumento: e.target['numeroDeDocumento'].value,
    fechaDeNacimiento: e.target['fecha_nacimiento'].value,
    telefono: e.target['numero_celular'].value,
    paisDeResidencia: e.target['pais_de_residencia'].value,
    genero: e.target['genero'].value
  };

  if (user.contrasena !== user.contrasenaConfirm) {
    alert('Las contraseñas no coinciden, intenta nuevamente.');
    return;
  }

  try {
    const response = await fetch(`${baseURL}/usuarios`, {
      method: 'POST',
      headers: { 
        'Content-Type': 'application/json',
       },
      body: JSON.stringify({email:user.email,
        contrasena:user.contrasena,
        nombre:user.nombre,
        apellido:user.apellido, 
        cedula: user.numeroDeDocumento,
        fechaDeNacimiento:user.fechaDeNacimiento, 
        telefono:user.telefono, 
        paisDeResidencia:user.paisDeResidencia, 
        genero:user.genero})
    });
    //const data = await response.json();

    if (response.ok){
      alert('Cuenta creada correctamente');
      updateNavbarUser();
      form.reset();
      showSection('home');
      //e.target.reset();
    }else{
      alert('algo ocurrio no fue creada la cuenta');
      form.reset();
    }
  } catch (error) {
    alert('Error al conectar: ' + error.message);
  }

  /*localStorage.setItem(
    'user',
    JSON.stringify(user)
  );*/
});

// LOGIN
document.getElementById('loginForm').addEventListener('submit', async (e) => {
  e.preventDefault();
  const form = e.target;
  const user = {
    email: e.target['correo'].value,
    contrasena: e.target['contrasena'].value,
  };
  try {
    const response = await fetch(`${baseURL}/login`, {
      method: 'POST',
      headers: { 
        'Content-Type': 'application/json',
       },
      body: JSON.stringify({
        email:user.email,
        contrasena:user.contrasena
      })
    });
    const data = await response.json();

    if (response.ok){
      alert('Inicio de sesión exitoso');
      sessionStorage.setItem('token', data.tokenJWT);
      getUserInformation();
      //e.target.reset();
    }else{
      alert('Error no se pudo iniciar sesion');
      form.reset();
    }
  } catch (error) {
    alert('Error al conectar: ' + error.message);
  }

  /*const user =
    JSON.parse(localStorage.getItem('user'));

  const email =
    document.getElementById('loginEmail').value;

  if (!user) {

    alert('No existe una cuenta');

    return;
  }

  if (user.email !== email) {

    alert('Correo incorrecto');

    return;
  }*/

});

async function getUserInformation(){
  const token = sessionStorage.getItem('token');
  try {
    const response = await fetch(`${baseURL}/usuarios`, {
      method: 'GET',
      headers: { 
        'Content-Type': 'application/json',
        'Authorization':`${token}`
      },
    });

    const data = await response.json();

    if (response.ok){
      const loginUser = {
        nombre: data.nombre,
        apellido: data.apellido,
        telefono: data.telefono,
        numeroDeDocumento: data.cedula,
        fechaDeNacimiento: data.fechaDeNacimiento,
        genero: data.genero,
        paisDeResidencia: data.paisDeResidencia,
        email: data.email,
        perfil: data.perfil
      
      };
      sessionStorage.setItem('loginUser', JSON.stringify(loginUser));
      updateNavbarUser();
      showSection('profile');
      //e.target.reset();
    }else{
      alert('Error no pudo obtener informacion');
      form.reset();
    }
  } catch (error) {
    alert('Error al conectar: ' + error.message);
  }
}


//Actualizacion
/*async function getUserTickets(){

  const token = sessionStorage.getItem('token');
  try {
    const response = await fetch(`${baseURL}/boletas`, {
      method: 'GET',
      headers: { 
        'Content-Type': 'application/json',
        'Authorization':`${token}`
      },
    });

    const data = await response.json();

    if (response.ok){
      const boletasUser = {
        id:data.id,
        usuario: data.nombre,
        asiento: data.apellido,
        fechaDeCompra: data.fechaDeCompra,
        fechaDeVencimiento: data.fechaDeVencimiento,
        estadoBoleta: data.estadoBoleta
      };
      sessionStorage.setItem('loginUser', JSON.stringify(loginUser))
      updateNavbarUser();
      showSection('profile');
      //e.target.reset();
    }else{
      alert('Error no pudo obtener informacion');
      form.reset();
    }
  } catch (error) {
    alert('Error al conectar: ' + error.message);
  }

}*/


async function cargarTickets() {

  const token =
    sessionStorage.getItem('token');

  try {

    const response = await fetch(`${baseURL}/boletas`, {
      method: 'GET',
      headers: { 
        'Content-Type': 'application/json',
        'Authorization':`${token}`
      },
    });

    const tickets = await response.json();

    const container =
      document.getElementById(
        'ticketsContainer'
      );

    // limpiar contenido anterior
    container.innerHTML = '';


    tickets.forEach(ticket => {

      const ticketHTML = `
        <div class="ticket-card"
        onclick="verTicket(${ticket.id})"
        >
        
          <div class="ticket-header">
            <h3>${ticket.evento}</h3>
          </div>

          <div class="ticket-body">
            <p>📍 ${ticket.asiento}</p>
            <p>📅 ${ticket.fechaDeCompra}</p>
            <p>📅 ${ticket.fechaDeVencimiento}</p>
            <p>📍 ${ticket.estadoBoleta}</p>
          </div>

          <div class="ticket-footer">
            Ticket válido
          </div>

        </div>
      `;

      container.innerHTML += ticketHTML;

    });

  } catch (error) {

    console.error(error);

  }

}



async function verTicket(id) {

  const token =
    sessionStorage.getItem('token');

  try {

    const response = await fetch(`${baseURL}/boletas/${id}/pdf`, {
      method: 'GET',
      headers: { 
        'Content-Type': 'application/json',
        'Authorization':`${token}`
      },
    });

    if (!response.ok) throw new Error('Error al descargar');

    const blob = await response.blob();
    const url = window.URL.createObjectURL(blob);

    const a = document.createElement('a');
    a.href = url;
    a.download = `boleta${id}.pdf`; // nombre del archivo
    document.body.appendChild(a);
    a.click();

    // Limpieza
    a.remove();
    window.URL.revokeObjectURL(url);

  } catch (error) {
    console.error('Error:', error);
  }

}

async function cargarEventos() {

  const token =
    sessionStorage.getItem('token');

  try {

    const response = await fetch(`${baseURL}/eventos`, {
      method: 'GET',
      headers: { 
        'Content-Type': 'application/json',
      },
    });

    const events = await response.json();

    const container =
      document.getElementById(
        'eventsContainer'
      );

    // limpiar contenido anterior
    container.innerHTML = '';

    events.forEach(events => {

      const eventHTML = `
        <div class="event-card">
          <div class="event-image"></div>
          <div class="event-content">
            <div class="event-top">
              <span class="event-category">
                ${events.categoria}
              </span>
              <span class="event-price">
                ${events.precio}
              </span>
            </div>

            <h3>
              ${events.nombre}
            </h3>

            <p>
              ${events.frasePromocional}
            </p>

            <div class="event-info">

              <span>
                📍 ${events.ciudad}
              </span>

              <span>
                📅 ${events.fechaInicialDelEvento}
              </span>

            </div>

            <button 
              onclick="addEventToCart('${events.nombre}',${events.precio})"
            >
              Agregar al carrito
            </button>
          </div>
        </div>
      `;

      container.innerHTML += eventHTML;

    });

  } catch (error) {

    console.error(error);

  }

}




// NAVBAR USER
async function updateNavbarUser() {

  const user = JSON.parse(sessionStorage.getItem('loginUser'));

  const registerShortcut =
    document.getElementById('registerShortcut');

  const loginShortcut =
    document.getElementById('loginShortcut');

  const profileShortcut =
    document.getElementById('profileShortcut');

  if (user) {
    
    cargarTickets();

    registerShortcut.style.display = 'none';

    loginShortcut.style.display = 'none';

    profileShortcut.style.display = 'flex';

    document.getElementById('profileName')
      .textContent =
      `${user.nombre} ${user.apellido}`;

    document.getElementById('profileEmail')
      .textContent =
      user.email;

    document.getElementById('profilePhone')
      .textContent =
      user.telefono;

    document.getElementById('profileCountry')
      .textContent = 
      user.paisDeResidencia;

  } else {

    registerShortcut.style.display = 'flex';

    loginShortcut.style.display = 'flex';

    profileShortcut.style.display = 'none';
  }
}


// LOGOUT
document.getElementById('logoutBtn')
?.addEventListener('click', () => {
  sessionStorage.removeItem('token')
  sessionStorage.removeItem('loginUser');
  location.reload();
});

// INIT
loadTheme();

renderCart();

updateNavbarUser();

showSection('home');