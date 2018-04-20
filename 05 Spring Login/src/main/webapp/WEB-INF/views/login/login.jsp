<%@include file="/WEB-INF/views/helper/template.jsp" %>

<div id="contents">    
    <div class="mdl-grid">
        <div class="mdl-cell mdl-cell--3-col">
        </div>

        <div class="mdl-cell mdl-cell--6-col">

            <sec:authorize access="isAnonymous()">
				<form action="${pageContext.request.contextPath}/login/authenticate" method="POST" role="form">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				    
				    <div class="wide-card mdl-card mdl-shadow--2dp min-spacer-up">
				    
				        <!-- Form header -->
				        <div class="mdl-card__title">
				            <h2 class="mdl-card__title-text">
			            		<spring:message code="login.login.form.title"/>
				            </h2>
				        </div>
				
				        <!-- Form body -->
				        <div class="mdl-card__supporting-text">
				
					        <!-- Error message -->
					        <c:if test="${not empty error}">
						        <div class="alert alert-danger">
						        	<spring:message code="${error}"/>
						        </div>
					        </c:if>
					
					        <!-- E-mail field -->
					        <div class="wide mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
					            <input class="mdl-textfield__input" type="text" name="username" id="user-email">
					            <label class="mdl-textfield__label" for="user-email">
					            		<spring:message code="login.login.label.email"/>:
					            </label>
					        </div>
					
					        <!-- Password field -->
					        <div class="wide mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
					            <input class="mdl-textfield__input" type="password" name="password" id="user-password">
					            <label class="mdl-textfield__label" for="user-password">
					                <spring:message code="login.login.label.password"/>:
					            </label>
					        </div>
				
				        </div>
				
				        <!-- Form buttons -->
				        <div class="mdl-card__actions mdl-card--border">
				       	 	<div class="left">
				                <button type="submit" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
				                    <spring:message code="login.login.button.submit"/>
				                </button>
				            </div>
				            <div class="right">
				            		<a href="${pageContext.request.contextPath}/login/create">
								    <button type="button" data-ng-click="create()" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
								    		<spring:message code="login.login.button.create.account"/>
								    </button>
							    </a>
							    <a href="${pageContext.request.contextPath}/login/forgot">
								    <button type="button" data-ng-click="forgot()" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
								    		<spring:message code="login.login.button.forgot.password"/>
								    </button>
								</a>
						    </div>
				        </div>
				    </div>
				</form>				
            </sec:authorize>

			<sec:authorize access="isAuthenticated()">
			    <p><spring:message code="login.login.message.already.authenticated"/></p>
			</sec:authorize>

        </div>

        <div class="mdl-cell mdl-cell--3-col">
        </div>
    </div>
</div>