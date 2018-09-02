defmodule HttpApiTester do
  defp postRequest(request) do
    HTTPoison.post request.url,
                   request.body,
                   [{"Content-Type", "application/json"}]
  end

  defp buildRequest() do
    %Request{
      url: "http://localhost:8081/api/orders",
      body: "{\"timestamp\": \"2018-08-23T07:30:03.000Z\", \"amount\": \"100.37\"}"
    }
  end

  defp buildNumberOfRequests(totalNumber, requests \\ [], currentNumber \\ 0) do
    cond do
      totalNumber === currentNumber ->
        requests
      true ->
        buildNumberOfRequests(totalNumber, [requests], currentNumber + 1)
    end
  end

  defp run(numberOfRequests, numberOfClients) do
    # send n requests over m concurrent clients

    # determine number of requests per client
    # instantiate m clients which iterate through their set of requests
    # collect total
    # expect total to equal stats sum
  end
end
