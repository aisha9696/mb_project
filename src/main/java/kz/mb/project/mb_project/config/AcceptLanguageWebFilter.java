package kz.mb.project.mb_project.config;


import java.io.IOException;
import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

@Component
@Order(1)
public class AcceptLanguageWebFilter implements Filter {

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    Locale locale = servletRequest.getLocale();
    LocaleContextHolder.setLocale(locale);
    filterChain.doFilter(servletRequest, servletResponse);
    LocaleContextHolder.resetLocaleContext();
  }
}
