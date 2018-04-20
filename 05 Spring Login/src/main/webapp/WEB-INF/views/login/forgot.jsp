<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="/WEB-INF/views/helper/template.jsp" %>

<div id="contents">
   <div class="mdl-grid">
        <div class="mdl-cell mdl-cell--3-col">
        </div>

        <div class="mdl-cell mdl-cell--6-col">
            <sec:authorize access="isAnonymous()">
                <form:form action="${pageContext.request.contextPath}/login/forgot" commandName="form" method="POST" enctype="utf8" role="form">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				    
				    <div class="wide-card mdl-card mdl-shadow--2dp min-spacer-up">
				    
				        <!-- Form header -->
				        <div class="mdl-card__title">
				            <h2 class="mdl-card__title-text">
				            <spring:message code="login.forgot.password.title"/>
				            </h2>
				        </div>
				
				        <!-- Form body -->
				        <div class="mdl-card__supporting-text">
				            <p class="mid-spacer-down">
					            <spring:message code="login.forgot.password.subtitle"/>
				            </p>
					
					        <!-- E-mail field -->
					        <div class="wide mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
					           	<form:input id="form-email" path="email" cssClass="mdl-textfield__input"/>
                            	<form:errors id="error-email" path="email" cssClass="error-block"/>				            
					            <label class="mdl-textfield__label" for="user-email"><spring:message code="login.forgot.password.label.email"/>:</label>
					        </div>
				        </div>
				
				        <!-- Form buttons -->
				        <div class="mdl-card__actions mdl-card--border">
				       	 	<div class="left">
				                <button type="submit" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
				                    <spring:message code="login.forgot.password.button.send"/>
				                </button>
				            </div>
				            <div class="right">
				            		<a href="${pageContext.request.contextPath}/login">
								    <button type="button" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
					                    <spring:message code="login.forgot.password.button.login"/>
								    </button>
							    </a>
						    </div>
				        </div>
				    </div>
				</form:form>
            </sec:authorize>
        </div>

        <div class="mdl-cell mdl-cell--3-col">
        </div>
    </div>
</div>