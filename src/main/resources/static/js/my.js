$(document).ready(function () {

    // $.get( "http://127.0.0.1:8080/map/getById?parm={parm}", { name: "John", time: "2pm" } );

    $("#button").click(function () {
        $.get("http://127.0.0.1:8080/getById", {parm: 1})
            .done(function (data) {
                // alert( "Data Loaded: " + data );
                // var obj = jQuery.parseJSON(data);
                // alert(obj['last_name']);
                alert(data['lastName'])
            })
            .fail(function () {
                alert("error");
            });
    });




    // $.get("http://127.0.0.1:8080/getAll")
    //     .done(function (data) {
    //         // alert( "Data Loaded: " + data );
    //         // var obj = jQuery.parseJSON(data);
    //         // alert(obj['last_name']);
    //         $('#names tbody').empty();
    //         for (var i = 0; i < data.length; i++) {
    //             $('#names tbody').append('<tr><td>' + data[i]['id'] + '</td><td>' + data[i]['firstName'] + '</td><td>' + data[i]['lastName'] + '</td><td>' + data[i]['middleName'] + '</td></tr>')
    //         }
    //
    //         alert(data)
    //     })
    //     .fail(function () {
    //         alert("error");
    //     });


});