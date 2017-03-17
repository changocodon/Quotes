function editSQuote(i) {
    var author = document.getElementById("name"+i);
    var quote = document.getElementById("quote"+i);
    
    document.getElementById("author").value = author.textContent;
    document.getElementById("quoteOfAuthor").value = quote.textContent;
    alert(author.textContent +': '+ quote.textContent);

}

function deleteSQuote(i) {
    var author = document.getElementById("name"+i);
    var quote = document.getElementById("quote"+i);
    alert(author.textContent +': '+ quote.textContent);
}