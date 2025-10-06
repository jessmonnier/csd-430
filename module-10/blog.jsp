<%@ taglib uri="/pagination" prefix="blog" %>

<!-- Some sort of blog content, blah blah -->

<!-- The variable values here would have to be supplied by some sort of servlet logic/http request info -->
<blog:pagination currentPage="${page}" totalPages="${totalPages}" baseUrl="blog.jsp?page=" />

<!-- Footer etc -->