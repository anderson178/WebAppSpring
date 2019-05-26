$(document).ready(function () {

    // $.get( "http://127.0.0.1:8080/map/getById?parm={parm}", { name: "John", time: "2pm" } );

    // $("#button").click(function () {
    //     $.get("http://127.0.0.1:8080/getById", {parm: 1})
    //         .done(function (data) {
    //             // alert( "Data Loaded: " + data );
    //             // var obj = jQuery.parseJSON(data);
    //             // alert(obj['last_name']);
    //             alert(data['lastName'])
    //         })
    //         .fail(function () {
    //             alert("error");
    //         });
    // });

    $("#fillButton").click(function () {
        $.get("http://127.0.0.1:8080/getAll")
            .done(function (data) {
                $('#names tbody').empty();
                for (var i = 0; i < data.length; i++) {
                    var date = new Date(data[i]['birthDate']);
                    // alert(date.getFullYear() + ' ' + date.getMonth()+' ' + date.getDate());
                    $('#names tbody').append('<tr><td><input type="checkbox" id="blahA" value="1"/></td>   <td>' + data[i]['id'] + '</td><td>' + data[i]['firstName'] + '</td><td>' + data[i]['lastName'] + '</td><td>' + data[i]['middleName'] + '</td>    <td>' + date.getFullYear() + ' ' + date.getMonth() + ' ' + date.getDate() + '</td>   <td>' + data[i]['comment'] + '</td></tr>')
                }
            })
    });

    $("#processButton").click(function () {
        var message = "Id Name                  Country\n";
        //Loop through all checked CheckBoxes in GridView.
        $("#names input[type=checkbox]:checked").each(function () {
            var row = $(this).closest("tr")[0];
            message += row.cells[1].innerHTML;
            message += "   " + row.cells[2].innerHTML;
            message += "   " + row.cells[3].innerHTML;
            message += "   " + row.cells[4].innerHTML;
            message += "\n";
        });
        alert(message);
        return false;
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