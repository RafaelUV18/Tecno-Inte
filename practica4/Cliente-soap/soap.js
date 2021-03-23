var n1='0';
var n2='0';
var r=document.getElementById('r');
var mensaje;

function set(){
    n1=document.getElementById('a').value;
    n2=document.getElementById('b').value;
    mensaje = '<?xml version="1.0" encoding="utf-8"?>' +
    '<Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">' +
        '<Body>' +
            '<SumarRequest xmlns="http://www.example.org/calculadora" >' +
                '<a>'+n1.toString()+'</a>' +
                '<b>'+n2.toString()+'</b>' +
            '</SumarRequest>' +
        '</Body>' +
    '</Envelope>';
}


var request, parser, xmlDoc;
function soap() {
    // r.value=5;
    set();
    axios.post('http://localhost:8081/ws/calculadora', mensaje,{
        headers:{
            'Content-Type': 'text/xml'
        }
    })
    .then(response => getValue(response.data))
    .catch(err => console.log(err));
}

function getValue(xml){
    parser = new DOMParser();
    xmlDoc = parser.parseFromString(xml,"text/xml")
    r.value = xmlDoc.getElementsByTagName("ns2:resultado")[0].childNodes[0].nodeValue;
}
