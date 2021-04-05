var peticion = '';

function ini() {
    peticion =
        '<?xml version="1.0" encoding="utf-8"?>' +
        '<Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">' +
        '<Body>' +
        '<PeliculaRequest xmlns="http://www.example.org/cine">' +
        '<a>' + document.getElementById('no.sala').value + '</a>' +
        '</PeliculaRequest>' +
        '</Body>' +
        '</Envelope>';
}

function soap() {
    ini();
    axios.post('http://54.174.105.47:8081/ws/cine.wsdl', peticion, {
        headers: {
            'Content-Type': 'text/xml'
        }
    })
        .then(function (response) {
            document.getElementById('lugares_D').value = resultado(response.data)
        })
        .catch(err => console.log(err));
}

function resultado(rxml) {
    parser = new DOMParser();
    xmlDoc = parser.parseFromString(rxml, "text/xml");
    result1 = xmlDoc.getElementsByTagName("ns2:lugaresD")[0].childNodes[0].nodeValue;
    result2 = xmlDoc.getElementsByTagName("ns2:lugaresO")[0].childNodes[0].nodeValue;
    result = parseInt(result1) - parseInt(result2);
    return result;
}