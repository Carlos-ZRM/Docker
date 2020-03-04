
// const $base_backend = `http://localhost/ecommerce_backend`;
const $base_backend = 'http://lacastellanaqa-112212876.us-east-1.elb.amazonaws.com/ecommerce_backend:8080';
let test = () =>{
    $.ajax({
        url: `${$base_backend}/api/prueba/200/`,
        method: 'GET',
        contentType: 'application/json',
        dataType: 'text'
    }).done((data) => {
        console.log(data);
        $("#texto").html(data);
    }).fail((qXHR, textStatus, errorThrown) => {
        
    });
}
$(document).ready(($) => {
    test();
});