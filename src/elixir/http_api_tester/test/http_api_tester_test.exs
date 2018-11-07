defmodule HttpApiTesterTest do
  use ExUnit.Case
  doctest HttpApiTester

#  test "sends requests at the API" do
#    # todo call API tester to load orders
#
#    {:ok, response} = HTTPoison.get("http://localhost:8081/api/statistics", [], [])
#
#    IO.puts response.body
#
#    assert response.status_code == 200
#  end

  test "correctly distributes requests across clients when evenly divisible" do
    result = HttpApiTester.distributeRequestsAcrossClients(300, 3)

    assert Enum.at(result, 0) == 100
    assert Enum.at(result, 1) == 100
    assert Enum.at(result, 2) == 100
  end

  test "correctly distributes requests across clients when a remainder is present" do
    result = HttpApiTester.distributeRequestsAcrossClients(14, 3)

    assert Enum.at(result, 0) == 5
    assert Enum.at(result, 1) == 5
    assert Enum.at(result, 2) == 4
  end
end