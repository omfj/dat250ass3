package com.example.dat250ass3.security

import com.example.dat250ass3.service.SessionService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class SessionFilter(
    private val sessionService: SessionService,
) : OncePerRequestFilter() {
    @Value("\${session.cookie.name}")
    private lateinit var sessionCookieName: String

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val cookies = request.cookies ?: emptyArray()

        val sessionId = cookies.find { it.name == sessionCookieName }?.value
        if (sessionId != null) {
            val user = sessionService.validateSessionAndGetUser(sessionId)
            if (user != null) {
                val authentication = SessionAuthenticationToken(user)
                SecurityContextHolder.getContext().authentication = authentication
            } else {
                SecurityContextHolder.clearContext()
            }
        } else {
            SecurityContextHolder.clearContext()
        }

        filterChain.doFilter(request, response)
    }
}
