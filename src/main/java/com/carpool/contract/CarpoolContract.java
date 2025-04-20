package com.carpool.contract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple8;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/hyperledger-web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.6.3.
 */
@SuppressWarnings("rawtypes")
public class CarpoolContract extends Contract {
    public static final String BINARY = "0x608060405234801561001057600080fd5b50612376806100206000396000f3fe6080604052600436106100a75760003560e01c8063d4126e6f11610064578063d4126e6f14610206578063d547d0991461024a578063e2c13e3214610273578063e83e6ec81461029c578063f8b11691146102c5578063f9e823e614610302576100a7565b806327a212ca146100ac578063439db3f7146100e9578063456352dd1461012657806363a36046146101635780639c89a0e21461018c578063b4b59f9a146101c9575b600080fd5b3480156100b857600080fd5b506100d360048036038101906100ce91906117bc565b61031e565b6040516100e09190612058565b60405180910390f35b3480156100f557600080fd5b50610110600480360381019061010b91906117fd565b61063c565b60405161011d9190611dce565b60405180910390f35b34801561013257600080fd5b5061014d600480360381019061014891906117fd565b610681565b60405161015a9190611dce565b60405180910390f35b34801561016f57600080fd5b5061018a600480360381019061018591906117fd565b6106c6565b005b34801561019857600080fd5b506101b360048036038101906101ae9190611757565b610a11565b6040516101c0919061207a565b60405180910390f35b3480156101d557600080fd5b506101f060048036038101906101eb91906117fd565b610a5a565b6040516101fd9190611dce565b60405180910390f35b34801561021257600080fd5b5061022d600480360381019061022891906117bc565b610acd565b604051610241989796959493929190611e65565b60405180910390f35b34801561025657600080fd5b50610271600480360381019061026c91906117bc565b610ce3565b005b34801561027f57600080fd5b5061029a60048036038101906102959190611780565b610fb2565b005b3480156102a857600080fd5b506102c360048036038101906102be9190611851565b61115d565b005b3480156102d157600080fd5b506102ec60048036038101906102e79190611757565b611386565b6040516102f9919061207a565b60405180910390f35b61031c600480360381019061031791906117bc565b61139e565b005b6103266115b2565b600073ffffffffffffffffffffffffffffffffffffffff1660008360405161034e9190611d8e565b908152602001604051809103902060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614156103d7576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016103ce90611f18565b60405180910390fd5b6000826040516103e79190611d8e565b908152602001604051809103902060405180610100016040529081600082018054610411906121e2565b80601f016020809104026020016040519081016040528092919081815260200182805461043d906121e2565b801561048a5780601f1061045f5761010080835404028352916020019161048a565b820191906000526020600020905b81548152906001019060200180831161046d57829003601f168201915b505050505081526020016001820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016002820180546104f9906121e2565b80601f0160208091040260200160405190810160405280929190818152602001828054610525906121e2565b80156105725780601f1061054757610100808354040283529160200191610572565b820191906000526020600020905b81548152906001019060200180831161055557829003601f168201915b5050505050815260200160038201805461058b906121e2565b80601f01602080910402602001604051908101604052809291908181526020018280546105b7906121e2565b80156106045780601f106105d957610100808354040283529160200191610604565b820191906000526020600020905b8154815290600101906020018083116105e757829003601f168201915b505050505081526020016004820154815260200160058201548152602001600682015481526020016007820154815250509050919050565b6001828051602081018201805184825260208301602085012081835280955050505050506020528060005260406000206000915091509054906101000a900460ff1681565b6002828051602081018201805184825260208301602085012081835280955050505050506020528060005260406000206000915091509054906101000a900460ff1681565b3373ffffffffffffffffffffffffffffffffffffffff166000836040516106ed9190611d8e565b908152602001604051809103902060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614610775576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161076c90611ff8565b60405180910390fd5b6001826040516107859190611d8e565b908152602001604051809103902060008273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff1661081d576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161081490611fd8565b60405180910390fd5b60028260405161082d9190611d8e565b908152602001604051809103902060008273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff16156108c6576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016108bd90611f38565b60405180910390fd5b600080836040516108d79190611d8e565b90815260200160405180910390206005015411610929576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161092090611fb8565b60405180910390fd5b600160028360405161093b9190611d8e565b908152602001604051809103902060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548160ff0219169083151502179055506000826040516109ad9190611d8e565b908152602001604051809103902060050160008154809291906109cf906121b8565b91905055507fd497983bd2fe9b6c906b7979f2736f69c6c12fe6859674057b49029f0c4419028282604051610a05929190611de9565b60405180910390a15050565b6000600360008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549050919050565b6000600283604051610a6c9190611d8e565b908152602001604051809103902060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff16905092915050565b600081805160208101820180518482526020830160208501208183528095505050505050600091509050806000018054610b06906121e2565b80601f0160208091040260200160405190810160405280929190818152602001828054610b32906121e2565b8015610b7f5780601f10610b5457610100808354040283529160200191610b7f565b820191906000526020600020905b815481529060010190602001808311610b6257829003601f168201915b5050505050908060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690806002018054610bba906121e2565b80601f0160208091040260200160405190810160405280929190818152602001828054610be6906121e2565b8015610c335780601f10610c0857610100808354040283529160200191610c33565b820191906000526020600020905b815481529060010190602001808311610c1657829003601f168201915b505050505090806003018054610c48906121e2565b80601f0160208091040260200160405190810160405280929190818152602001828054610c74906121e2565b8015610cc15780601f10610c9657610100808354040283529160200191610cc1565b820191906000526020600020905b815481529060010190602001808311610ca457829003601f168201915b5050505050908060040154908060050154908060060154908060070154905088565b600073ffffffffffffffffffffffffffffffffffffffff16600082604051610d0b9190611d8e565b908152602001604051809103902060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161415610d94576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610d8b90611f18565b60405180910390fd5b60008082604051610da59190611d8e565b90815260200160405180910390206007015414610df7576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610dee90612018565b60405180910390fd5b60008082604051610e089190611d8e565b90815260200160405180910390206005015411610e5a576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610e5190611fb8565b60405180910390fd5b600181604051610e6a9190611d8e565b908152602001604051809103902060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff1615610f03576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610efa90611f98565b60405180910390fd5b60018082604051610f149190611d8e565b908152602001604051809103902060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548160ff0219169083151502179055507f5b24afe20ce8a9651d0ace72cb4cd8dc87fb5f59c8dade9dacdc6f7dd7b51f798133604051610fa7929190611de9565b60405180910390a150565b8173ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415611021576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161101890612038565b60405180910390fd5b801561108157600360008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600081548092919061107790612214565b9190505550611120565b6000600360008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054111561111f57600360008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000815480929190611119906121b8565b91905055505b5b7f6a0825bd9ef0835bc2a4ad8be1b68a6d07df69aad599945632128500369cdd038282604051611151929190611da5565b60405180910390a15050565b600073ffffffffffffffffffffffffffffffffffffffff166000876040516111859190611d8e565b908152602001604051809103902060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161461120d576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161120490611f78565b60405180910390fd5b6040518061010001604052808781526020013373ffffffffffffffffffffffffffffffffffffffff168152602001868152602001858152602001848152602001838152602001828152602001600081525060008760405161126e9190611d8e565b9081526020016040518091039020600082015181600001908051906020019061129892919061160d565b5060208201518160010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060408201518160020190805190602001906112fc92919061160d565b50606082015181600301908051906020019061131992919061160d565b506080820151816004015560a0820151816005015560c0820151816006015560e082015181600701559050507fd8d4238d6134dc2e868f1a56ac5e4c0a6e225bdbb1694c21df86229ca76c423e8633604051611376929190611de9565b60405180910390a1505050505050565b60036020528060005260406000206000915090505481565b6002816040516113ae9190611d8e565b908152602001604051809103902060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff16611446576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161143d90611ef8565b60405180910390fd5b6000816040516114569190611d8e565b90815260200160405180910390206006015434146114a9576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016114a090611f58565b60405180910390fd5b6000816040516114b99190611d8e565b908152602001604051809103902060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc349081150290604051600060405180830381858888f19350505050158015611530573d6000803e3d6000fd5b507f8747f757e88901b58d00d8f24ecf641b88a073fb206fc1ec91e91c7a3b116fe081336000846040516115649190611d8e565b908152602001604051809103902060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16346040516115a79493929190611e19565b60405180910390a150565b60405180610100016040528060608152602001600073ffffffffffffffffffffffffffffffffffffffff1681526020016060815260200160608152602001600081526020016000815260200160008152602001600081525090565b828054611619906121e2565b90600052602060002090601f01602090048101928261163b5760008555611682565b82601f1061165457805160ff1916838001178555611682565b82800160010185558215611682579182015b82811115611681578251825591602001919060010190611666565b5b50905061168f9190611693565b5090565b5b808211156116ac576000816000905550600101611694565b5090565b60006116c36116be846120c6565b612095565b9050828152602081018484840111156116db57600080fd5b6116e6848285612176565b509392505050565b6000813590506116fd816122fb565b92915050565b60008135905061171281612312565b92915050565b600082601f83011261172957600080fd5b81356117398482602086016116b0565b91505092915050565b60008135905061175181612329565b92915050565b60006020828403121561176957600080fd5b6000611777848285016116ee565b91505092915050565b6000806040838503121561179357600080fd5b60006117a1858286016116ee565b92505060206117b285828601611703565b9150509250929050565b6000602082840312156117ce57600080fd5b600082013567ffffffffffffffff8111156117e857600080fd5b6117f484828501611718565b91505092915050565b6000806040838503121561181057600080fd5b600083013567ffffffffffffffff81111561182a57600080fd5b61183685828601611718565b9250506020611847858286016116ee565b9150509250929050565b60008060008060008060c0878903121561186a57600080fd5b600087013567ffffffffffffffff81111561188457600080fd5b61189089828a01611718565b965050602087013567ffffffffffffffff8111156118ad57600080fd5b6118b989828a01611718565b955050604087013567ffffffffffffffff8111156118d657600080fd5b6118e289828a01611718565b94505060606118f389828a01611742565b935050608061190489828a01611742565b92505060a061191589828a01611742565b9150509295509295509295565b61192b8161212e565b82525050565b61193a8161212e565b82525050565b61194981612140565b82525050565b600061195a826120f6565b6119648185612101565b9350611974818560208601612185565b61197d816122ea565b840191505092915050565b6000611993826120f6565b61199d8185612112565b93506119ad818560208601612185565b6119b6816122ea565b840191505092915050565b60006119cc826120f6565b6119d68185612123565b93506119e6818560208601612185565b80840191505092915050565b60006119ff601583612112565b91507f4e6f7420616363657074656420666f72207269646500000000000000000000006000830152602082019050919050565b6000611a3f601383612112565b91507f5269646520646f6573206e6f74206578697374000000000000000000000000006000830152602082019050919050565b6000611a7f601083612112565b91507f416c7265616479206163636570746564000000000000000000000000000000006000830152602082019050919050565b6000611abf601883612112565b91507f496e636f7272656374207061796d656e7420616d6f756e7400000000000000006000830152602082019050919050565b6000611aff601383612112565b91507f5269646520616c726561647920657869737473000000000000000000000000006000830152602082019050919050565b6000611b3f601183612112565b91507f416c7265616479207265717565737465640000000000000000000000000000006000830152602082019050919050565b6000611b7f601283612112565b91507f4e6f20736561747320617661696c61626c6500000000000000000000000000006000830152602082019050919050565b6000611bbf600f83612112565b91507f4e6f2073756368207265717565737400000000000000000000000000000000006000830152602082019050919050565b6000611bff600f83612112565b91507f4e6f74207269646520706f7374657200000000000000000000000000000000006000830152602082019050919050565b6000611c3f601283612112565b91507f52696465206e6f7420617661696c61626c6500000000000000000000000000006000830152602082019050919050565b6000611c7f601483612112565b91507f43616e6e6f74207261746520796f757273656c660000000000000000000000006000830152602082019050919050565b6000610100830160008301518482036000860152611cd0828261194f565b9150506020830151611ce56020860182611922565b5060408301518482036040860152611cfd828261194f565b91505060608301518482036060860152611d17828261194f565b9150506080830151611d2c6080860182611d70565b5060a0830151611d3f60a0860182611d70565b5060c0830151611d5260c0860182611d70565b5060e0830151611d6560e0860182611d70565b508091505092915050565b611d798161216c565b82525050565b611d888161216c565b82525050565b6000611d9a82846119c1565b915081905092915050565b6000604082019050611dba6000830185611931565b611dc76020830184611940565b9392505050565b6000602082019050611de36000830184611940565b92915050565b60006040820190508181036000830152611e038185611988565b9050611e126020830184611931565b9392505050565b60006080820190508181036000830152611e338187611988565b9050611e426020830186611931565b611e4f6040830185611931565b611e5c6060830184611d7f565b95945050505050565b6000610100820190508181036000830152611e80818b611988565b9050611e8f602083018a611931565b8181036040830152611ea18189611988565b90508181036060830152611eb58188611988565b9050611ec46080830187611d7f565b611ed160a0830186611d7f565b611ede60c0830185611d7f565b611eeb60e0830184611d7f565b9998505050505050505050565b60006020820190508181036000830152611f11816119f2565b9050919050565b60006020820190508181036000830152611f3181611a32565b9050919050565b60006020820190508181036000830152611f5181611a72565b9050919050565b60006020820190508181036000830152611f7181611ab2565b9050919050565b60006020820190508181036000830152611f9181611af2565b9050919050565b60006020820190508181036000830152611fb181611b32565b9050919050565b60006020820190508181036000830152611fd181611b72565b9050919050565b60006020820190508181036000830152611ff181611bb2565b9050919050565b6000602082019050818103600083015261201181611bf2565b9050919050565b6000602082019050818103600083015261203181611c32565b9050919050565b6000602082019050818103600083015261205181611c72565b9050919050565b600060208201905081810360008301526120728184611cb2565b905092915050565b600060208201905061208f6000830184611d7f565b92915050565b6000604051905081810181811067ffffffffffffffff821117156120bc576120bb6122bb565b5b8060405250919050565b600067ffffffffffffffff8211156120e1576120e06122bb565b5b601f19601f8301169050602081019050919050565b600081519050919050565b600082825260208201905092915050565b600082825260208201905092915050565b600081905092915050565b60006121398261214c565b9050919050565b60008115159050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b82818337600083830152505050565b60005b838110156121a3578082015181840152602081019050612188565b838111156121b2576000848401525b50505050565b60006121c38261216c565b915060008214156121d7576121d661225d565b5b600182039050919050565b600060028204905060018216806121fa57607f821691505b6020821081141561220e5761220d61228c565b5b50919050565b600061221f8261216c565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8214156122525761225161225d565b5b600182019050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b6000601f19601f8301169050919050565b6123048161212e565b811461230f57600080fd5b50565b61231b81612140565b811461232657600080fd5b50565b6123328161216c565b811461233d57600080fd5b5056fea2646970667358221220a7351d307e4f2a6c1b7044e6889a41ce05c5ca50fc2500d17b50bf2fdc8d98b164736f6c63430008000033";

    private static String librariesLinkedBinary;

    public static final String FUNC_ACCEPTEDREQUESTS = "acceptedRequests";

    public static final String FUNC_RIDEREQUESTS = "rideRequests";

    public static final String FUNC_RIDES = "rides";

    public static final String FUNC_USERREPUTATIONS = "userReputations";

    public static final String FUNC_CREATERIDE = "createRide";

    public static final String FUNC_REQUESTRIDE = "requestRide";

    public static final String FUNC_ACCEPTRIDEREQUEST = "acceptRideRequest";

    public static final String FUNC_MAKEPAYMENT = "makePayment";

    public static final String FUNC_UPDATEREPUTATION = "updateReputation";

    public static final String FUNC_GETRIDEINFO = "getRideInfo";

    public static final String FUNC_ISRIDEREQUESTACCEPTED = "isRideRequestAccepted";

    public static final String FUNC_GETREPUTATION = "getReputation";

    public static final Event PAYMENTMADE_EVENT = new Event("PaymentMade", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event REPUTATIONUPDATED_EVENT = new Event("ReputationUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Bool>() {}));
    ;

    public static final Event RIDEACCEPTED_EVENT = new Event("RideAccepted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event RIDECREATED_EVENT = new Event("RideCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event RIDEREQUESTED_EVENT = new Event("RideRequested", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("137", "0x1dF7Bb26E451a3D90F40428402B6B6A7616A1e18");
    }

    @Deprecated
    protected CarpoolContract(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected CarpoolContract(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected CarpoolContract(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected CarpoolContract(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<PaymentMadeEventResponse> getPaymentMadeEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(PAYMENTMADE_EVENT, transactionReceipt);
        ArrayList<PaymentMadeEventResponse> responses = new ArrayList<PaymentMadeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PaymentMadeEventResponse typedResponse = new PaymentMadeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.rideId = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.from = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.to = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static PaymentMadeEventResponse getPaymentMadeEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(PAYMENTMADE_EVENT, log);
        PaymentMadeEventResponse typedResponse = new PaymentMadeEventResponse();
        typedResponse.log = log;
        typedResponse.rideId = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.from = (String) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.to = (String) eventValues.getNonIndexedValues().get(2).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
        return typedResponse;
    }

    public Flowable<PaymentMadeEventResponse> paymentMadeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getPaymentMadeEventFromLog(log));
    }

    public Flowable<PaymentMadeEventResponse> paymentMadeEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PAYMENTMADE_EVENT));
        return paymentMadeEventFlowable(filter);
    }

    public static List<ReputationUpdatedEventResponse> getReputationUpdatedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(REPUTATIONUPDATED_EVENT, transactionReceipt);
        ArrayList<ReputationUpdatedEventResponse> responses = new ArrayList<ReputationUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ReputationUpdatedEventResponse typedResponse = new ReputationUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.isPositive = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ReputationUpdatedEventResponse getReputationUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(REPUTATIONUPDATED_EVENT, log);
        ReputationUpdatedEventResponse typedResponse = new ReputationUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.isPositive = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<ReputationUpdatedEventResponse> reputationUpdatedEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getReputationUpdatedEventFromLog(log));
    }

    public Flowable<ReputationUpdatedEventResponse> reputationUpdatedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REPUTATIONUPDATED_EVENT));
        return reputationUpdatedEventFlowable(filter);
    }

    public static List<RideAcceptedEventResponse> getRideAcceptedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(RIDEACCEPTED_EVENT, transactionReceipt);
        ArrayList<RideAcceptedEventResponse> responses = new ArrayList<RideAcceptedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RideAcceptedEventResponse typedResponse = new RideAcceptedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.rideId = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.accepter = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static RideAcceptedEventResponse getRideAcceptedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(RIDEACCEPTED_EVENT, log);
        RideAcceptedEventResponse typedResponse = new RideAcceptedEventResponse();
        typedResponse.log = log;
        typedResponse.rideId = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.accepter = (String) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<RideAcceptedEventResponse> rideAcceptedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getRideAcceptedEventFromLog(log));
    }

    public Flowable<RideAcceptedEventResponse> rideAcceptedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(RIDEACCEPTED_EVENT));
        return rideAcceptedEventFlowable(filter);
    }

    public static List<RideCreatedEventResponse> getRideCreatedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(RIDECREATED_EVENT, transactionReceipt);
        ArrayList<RideCreatedEventResponse> responses = new ArrayList<RideCreatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RideCreatedEventResponse typedResponse = new RideCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.rideId = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.posterAddress = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static RideCreatedEventResponse getRideCreatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(RIDECREATED_EVENT, log);
        RideCreatedEventResponse typedResponse = new RideCreatedEventResponse();
        typedResponse.log = log;
        typedResponse.rideId = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.posterAddress = (String) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<RideCreatedEventResponse> rideCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getRideCreatedEventFromLog(log));
    }

    public Flowable<RideCreatedEventResponse> rideCreatedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(RIDECREATED_EVENT));
        return rideCreatedEventFlowable(filter);
    }

    public static List<RideRequestedEventResponse> getRideRequestedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(RIDEREQUESTED_EVENT, transactionReceipt);
        ArrayList<RideRequestedEventResponse> responses = new ArrayList<RideRequestedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RideRequestedEventResponse typedResponse = new RideRequestedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.rideId = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.requester = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static RideRequestedEventResponse getRideRequestedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(RIDEREQUESTED_EVENT, log);
        RideRequestedEventResponse typedResponse = new RideRequestedEventResponse();
        typedResponse.log = log;
        typedResponse.rideId = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.requester = (String) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<RideRequestedEventResponse> rideRequestedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getRideRequestedEventFromLog(log));
    }

    public Flowable<RideRequestedEventResponse> rideRequestedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(RIDEREQUESTED_EVENT));
        return rideRequestedEventFlowable(filter);
    }

    public RemoteFunctionCall<Boolean> acceptedRequests(String param0, String param1) {
        final Function function = new Function(FUNC_ACCEPTEDREQUESTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0), 
                new org.web3j.abi.datatypes.Address(param1)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> rideRequests(String param0, String param1) {
        final Function function = new Function(FUNC_RIDEREQUESTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0), 
                new org.web3j.abi.datatypes.Address(param1)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Tuple8<String, String, String, String, BigInteger, BigInteger, BigInteger, BigInteger>> rides(
            String param0) {
        final Function function = new Function(FUNC_RIDES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple8<String, String, String, String, BigInteger, BigInteger, BigInteger, BigInteger>>(function,
                new Callable<Tuple8<String, String, String, String, BigInteger, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple8<String, String, String, String, BigInteger, BigInteger, BigInteger, BigInteger> call(
                            ) throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple8<String, String, String, String, BigInteger, BigInteger, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue(), 
                                (BigInteger) results.get(7).getValue());
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> userReputations(String param0) {
        final Function function = new Function(FUNC_USERREPUTATIONS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> createRide(String rideId, String startLocation,
            String endLocation, BigInteger departureTime, BigInteger availableSeats,
            BigInteger fare) {
        final Function function = new Function(
                FUNC_CREATERIDE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(rideId), 
                new org.web3j.abi.datatypes.Utf8String(startLocation), 
                new org.web3j.abi.datatypes.Utf8String(endLocation), 
                new org.web3j.abi.datatypes.generated.Uint256(departureTime), 
                new org.web3j.abi.datatypes.generated.Uint256(availableSeats), 
                new org.web3j.abi.datatypes.generated.Uint256(fare)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> requestRide(String rideId) {
        final Function function = new Function(
                FUNC_REQUESTRIDE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(rideId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> acceptRideRequest(String rideId,
            String requester) {
        final Function function = new Function(
                FUNC_ACCEPTRIDEREQUEST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(rideId), 
                new org.web3j.abi.datatypes.Address(requester)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> makePayment(String rideId, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_MAKEPAYMENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(rideId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> updateReputation(String user,
            Boolean isPositive) {
        final Function function = new Function(
                FUNC_UPDATEREPUTATION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(user), 
                new org.web3j.abi.datatypes.Bool(isPositive)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<RideInfo> getRideInfo(String rideId) {
        final Function function = new Function(FUNC_GETRIDEINFO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(rideId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<RideInfo>() {}));
        return executeRemoteCallSingleValueReturn(function, RideInfo.class);
    }

    public RemoteFunctionCall<Boolean> isRideRequestAccepted(String rideId, String requester) {
        final Function function = new Function(FUNC_ISRIDEREQUESTACCEPTED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(rideId), 
                new org.web3j.abi.datatypes.Address(requester)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<BigInteger> getReputation(String user) {
        final Function function = new Function(FUNC_GETREPUTATION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(user)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @Deprecated
    public static CarpoolContract load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new CarpoolContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static CarpoolContract load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CarpoolContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static CarpoolContract load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new CarpoolContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static CarpoolContract load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new CarpoolContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<CarpoolContract> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CarpoolContract.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<CarpoolContract> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CarpoolContract.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static RemoteCall<CarpoolContract> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CarpoolContract.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<CarpoolContract> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CarpoolContract.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class RideInfo extends DynamicStruct {
        public String rideId;

        public String posterAddress;

        public String startLocation;

        public String endLocation;

        public BigInteger departureTime;

        public BigInteger availableSeats;

        public BigInteger fare;

        public BigInteger status;

        public RideInfo(String rideId, String posterAddress, String startLocation,
                String endLocation, BigInteger departureTime, BigInteger availableSeats,
                BigInteger fare, BigInteger status) {
            super(new org.web3j.abi.datatypes.Utf8String(rideId), 
                    new org.web3j.abi.datatypes.Address(posterAddress), 
                    new org.web3j.abi.datatypes.Utf8String(startLocation), 
                    new org.web3j.abi.datatypes.Utf8String(endLocation), 
                    new org.web3j.abi.datatypes.generated.Uint256(departureTime), 
                    new org.web3j.abi.datatypes.generated.Uint256(availableSeats), 
                    new org.web3j.abi.datatypes.generated.Uint256(fare), 
                    new org.web3j.abi.datatypes.generated.Uint256(status));
            this.rideId = rideId;
            this.posterAddress = posterAddress;
            this.startLocation = startLocation;
            this.endLocation = endLocation;
            this.departureTime = departureTime;
            this.availableSeats = availableSeats;
            this.fare = fare;
            this.status = status;
        }

        public RideInfo(Utf8String rideId, Address posterAddress, Utf8String startLocation,
                Utf8String endLocation, Uint256 departureTime, Uint256 availableSeats, Uint256 fare,
                Uint256 status) {
            super(rideId, posterAddress, startLocation, endLocation, departureTime, availableSeats, fare, status);
            this.rideId = rideId.getValue();
            this.posterAddress = posterAddress.getValue();
            this.startLocation = startLocation.getValue();
            this.endLocation = endLocation.getValue();
            this.departureTime = departureTime.getValue();
            this.availableSeats = availableSeats.getValue();
            this.fare = fare.getValue();
            this.status = status.getValue();
        }
    }

    public static class PaymentMadeEventResponse extends BaseEventResponse {
        public String rideId;

        public String from;

        public String to;

        public BigInteger amount;
    }

    public static class ReputationUpdatedEventResponse extends BaseEventResponse {
        public String user;

        public Boolean isPositive;
    }

    public static class RideAcceptedEventResponse extends BaseEventResponse {
        public String rideId;

        public String accepter;
    }

    public static class RideCreatedEventResponse extends BaseEventResponse {
        public String rideId;

        public String posterAddress;
    }

    public static class RideRequestedEventResponse extends BaseEventResponse {
        public String rideId;

        public String requester;
    }
}
