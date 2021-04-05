var mensaje = '';

function iniciar() {
    mensaje =
        '<?xml version="1.0" encoding="utf-8"?>' +
        '<Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">' +
        '<Body>' +
        '<ReservacionRequest xmlns="http://www.example.org/cine">' +
            '<usuario>' + document.getElementById('usuario').value + '</usuario>' +
            '<sala>' + document.getElementById('no.sala').value + '</sala>' +
            '<lugaresR>' + document.getElementById('lugares_A').value + '</lugaresR>' +
        '</ReservacionRequest>' +
        '</Body>' +
        '</Envelope>';
}


function reservacion() {
    iniciar();
    axios.post('http://54.174.105.47:8081/ws/cine.wsdl', mensaje, {
        headers: {
            'Content-Type': 'text/xml'
        }
    })
        .then(function (response) {
            alert("Se han reservado los lugares con el ID: " + resultados(response.data));
        })
        .catch(err => console.log(err));
}

function resultados(rxml) {
    parser = new DOMParser();
    xmlDoc = parser.parseFromString(rxml, "text/xml");
    result = xmlDoc.getElementsByTagName("ns2:ReservacionID")[0].childNodes[0].nodeValue;
    return result;
}