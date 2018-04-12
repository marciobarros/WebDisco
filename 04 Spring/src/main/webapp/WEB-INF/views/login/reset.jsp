<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="/WEB-INF/views/helper/template.jsp" %>

<div id="contents">
   <div class="mdl-grid">
        <div class="mdl-cell mdl-cell--3-col">
        </div>

        <div class="mdl-cell mdl-cell--6-col">
            <sec:authorize access="isAnonymous()">
                <form:form action="${pageContext.request.contextPath}/login/reset" commandName="form" method="POST" enctype="utf8" role="form">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <form:input type="hidden" path="email"/>
                    <form:input type="hidden" path="token"/>
				    
				    <div class="wide-card mdl-card mdl-shadow--2dp min-spacer-up">
				    
				        <!-- Form header -->
				        <div class="mdl-card__title">
				            <h2 class="mdl-card__title-text">
				            <spring:message code="login.reset.password.title"/>
				            </h2>
				        </div>
				
				        <!-- Form body -->
				        <div class="mdl-card__supporting-text">
					        <!-- New password field -->
					        <div class="wide mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
					           	<form:password id="form-newPassword" path="newPassword" cssClass="mdl-textfield__input"/>
                            	<form:errors id="error-newPassword" path="newPassword" cssClass="error-block"/>				            
					            <label class="mdl-textfield__label" for="form-newPassword"><spring:message code="login.reset.password.label.new.password"/>:</label>
					        </div>
					        
					
					        <!-- Repeat password field -->
					        <div class="wide mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
					           	<form:password id="form-repeatNewPassword" path="repeatNewPassword" cssClass="mdl-textfield__input"/>
                            	<form:errors id="error-repeatNewPassword" path="repeatNewPassword" cssClass="error-block"/>				            
					            <label class="mdl-textfield__label" for="form-repeatNewPassword"><spring:message code="login.reset.password.label.new.password.verification"/>:</label>
					        </div>
				        </div>
				
				        <!-- Form buttons -->
				        <div class="mdl-card__actions mdl-card--border">
				       	 	<div class="left">
				                <button type="submit" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
				                    <spring:message code="login.reset.password.button.send"/>
				                </button>
				            </div>
				            <div class="right">
				            		<a href="${pageContext.request.contextPath}/login">
								    <button type="button" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
					                    <spring:message code="login.reset.password.button.login"/>
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