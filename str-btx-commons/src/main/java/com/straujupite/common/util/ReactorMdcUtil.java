package com.straujupite.common.util;

import java.util.function.Consumer;
import org.slf4j.MDC;
import reactor.core.publisher.Signal;

public class ReactorMdcUtil {

  public static <T> Consumer<Signal<T>> logOnNext(Consumer<T> logStatement) {
    return signal -> {
      if (signal.isOnNext()) {
        try {
          var context = signal.getContextView();
          context.getOrEmpty("traceId")
                 .ifPresent(traceId -> MDC.put("traceId", traceId.toString()));
          logStatement.accept(signal.get());
        } finally {
          MDC.clear();
        }
      }
    };
  }

  public static <T> Consumer<Signal<T>> logOnComplete(Runnable logStatement) {
    return signal -> {
      if (signal.isOnComplete()) {
        try {
          var context = signal.getContextView();
          context.getOrEmpty("traceId")
                 .ifPresent(traceId -> MDC.put("traceId", traceId.toString()));
          logStatement.run();
        } finally {
          MDC.clear();
        }
      }
    };
  }

  public static <T> Consumer<Signal<T>> logOnError(Consumer<Throwable> logStatement) {
    return signal -> {
      if (signal.isOnError()) {
        try {
          var context = signal.getContextView();
          context.getOrEmpty("traceId")
                 .ifPresent(traceId -> MDC.put("traceId", traceId.toString()));
          logStatement.accept(signal.getThrowable());
        } finally {
          MDC.clear();
        }
      }
    };
  }
}
